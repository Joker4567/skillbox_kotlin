curl -X POST -H "Authorization: key=AAAATx0pvgY:APA91bH4Sx5hC0Fvv7RrCMBGZeILxJmgs2GroY-Zx8qpU7ytLAeXuaiPjt0lvyOI0L_65bevZFNP0Dhv4rZkMNRp-67oxOF_DvEESygjX02gSAW6DMSCJkHnMqx70oVBPYqxlg1rWL3K" -H "Content-Type: application/json" -d '{
    "to":"f8L3Itp6RxOpZZZmaKA932:APA91bFLBUW3Y4Tw6zDocLQ98mi9a1V7xYbaMKS1ei9hFAx3Fl-VAkRm6Qy18qPiBLqBpqoegcUv4oS8pERmMD1_T3zqluYMqhhz0R_i8WZlINqXstfkeVrHJ5QsfrMm6ApZgh5Dm5Mr",
    "data": {
        "type" : "SALE",
        "data" : {
            "description" : "Описание",
            "title" : "Заголовок",
            "imageUrl" : "https://i.ytimg.com/vi/7aja79UIcd4/maxresdefault.jpg"
        }
    }
}' https://fcm.googleapis.com/fcm/send -v -i


curl -X POST -H "Authorization: key=AAAATx0pvgY:APA91bH4Sx5hC0Fvv7RrCMBGZeILxJmgs2GroY-Zx8qpU7ytLAeXuaiPjt0lvyOI0L_65bevZFNP0Dhv4rZkMNRp-67oxOF_DvEESygjX02gSAW6DMSCJkHnMqx70oVBPYqxlg1rWL3K" -H "Content-Type: application/json" -d '{
    "to":"f8L3Itp6RxOpZZZmaKA932:APA91bFLBUW3Y4Tw6zDocLQ98mi9a1V7xYbaMKS1ei9hFAx3Fl-VAkRm6Qy18qPiBLqBpqoegcUv4oS8pERmMD1_T3zqluYMqhhz0R_i8WZlINqXstfkeVrHJ5QsfrMm6ApZgh5Dm5Mr",
    "data": {
        "type" : "MESSAGE",
        "data" : {
            "userId" : 5673,
            "userName" : "Валера",
            "created_at" : 65365343534,
            "text" : "Сообщение из чата"
        }
    }
}' https://fcm.googleapis.com/fcm/send -v -i