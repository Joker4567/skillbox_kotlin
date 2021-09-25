package com.prognozrnm.data.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.prognozrnm.data.storage.Pref;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class PhotoUpload {

    static ScheduledThreadPoolExecutor threadPool = new ScheduledThreadPoolExecutor(8);
    static Handler handler = new Handler(Looper.getMainLooper());

    //Получить текущее время в UNIX-Time
    static long getTimestampWithGMT() {
        return System.currentTimeMillis() / 1000L;
    }

    public static void sendPhoto(byte[] data, int resultId, int paramId, Context context) {
        Runnable sendPhoto = new Runnable() {
            public void run() {
                try {
                    final Runnable sendPhoto = this;
                    ByteArrayOutputStream os = new ByteArrayOutputStream(data.length);
                    BitmapFactory.decodeByteArray(data, 0, data.length).compress(Bitmap.CompressFormat.JPEG, 90, os);
                    Pref pref = new Pref(context);
                    String token = pref.getAuthToken();
                    if(token == null)
                        token = "";
                    //Отправка фото на сервер через API
                    //http://demo.girngm.ru/Prospector.API/api/CheckList/UploadPhotos?timestamp=1568937600&assignmentsId=14&paramsId=3&comment=комментарий
                    sendFormData("http://demo.girngm.ru/Prospector.API/api/CheckList/UploadPhotos" +
                                    "?timestamp=" + (PhotoUpload.getTimestampWithGMT()) +
                                    "&assignmentsId=" + resultId +
                                    "&paramsId=" + paramId +
                                    "&comment=" + URLEncoder.encode("photo", "UTF-8"),
                            Arrays.asList(new FormData("file", os.toByteArray(), "1.jpg", "image/jpeg")),
                            token
                    );
                } catch (UnsupportedEncodingException ex) {
                    Log.d("Photo",ex.getMessage());
                }
            }
        };
        PhotoUpload.handler.postDelayed(sendPhoto, 50);
    }

    static void sendFormData(String url, Iterable<FormData> formData, String auth) {
        threadPool.execute(() -> {
            try {
                String lineEnd = "\r\n";
                String twoHyphens = "--";
                String boundary = "MobileInspector" + (int) (Math.random() * 1_000_000_000);

                final HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setConnectTimeout(120_000);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                if (auth != null) connection.setRequestProperty("Authorization", auth);
                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setUseCaches(false);

                DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
                for (FormData data : formData) {
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"" + data.getName() + '"');
                    if (data.getFileName() != null)
                        dos.writeBytes("; filename=\"" + URLEncoder.encode(data.getFileName(), "UTF-8") + '"');
                    dos.writeBytes(lineEnd);
                    if (data.getType() != null)
                        dos.writeBytes("Content-Type: " + data.getType() + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.write(data.getData());
                    dos.writeBytes(lineEnd);
                }
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                dos.flush();
                dos.close();

                readHttpResponse(connection);
            } catch (final Exception ex) {
                handler.postDelayed(() -> {
                    sendFormData(url, formData, auth);
                }, 100);
            }
        });
    }

    static void readHttpResponse(HttpURLConnection connection) throws IOException {
        final int code = connection.getResponseCode();
        if (code >= 400) {
            Log.d("Photo",connection.getResponseMessage());
        } else {
            final InputStream is = connection.getInputStream();
            final String res = streamToString(is);
            is.close();
            connection.disconnect();
        }
    }

    static String streamToString(InputStream is) throws IOException {
        return streamToString(is, "UTF-8");
    }

    static String streamToString(InputStream is, String encoding) throws IOException {
        int len = Math.max(is.available(), 1024), val, i = 0;
        byte[] buf = new byte[len], t;
        for (; (val = is.read()) != -1; ++i) {
            if (i >= len) {
                t = buf;
                buf = new byte[len <<= 1];
                System.arraycopy(t, 0, buf, 0, i);
            }
            buf[i] = (byte) val;
        }
        return new String(buf, 0, i, encoding);
    }
}
