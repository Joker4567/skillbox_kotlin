package com.example.contentprovider.Data

import android.content.ContentProviderOperation
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.provider.ContactsContract
import androidx.core.content.FileProvider
import com.example.contentprovider.BuildConfig
import com.example.contentprovider.Networking.Networking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import java.io.File


class Repository(private val context: Context) {
    private val avatars = arrayListOf(
        "https://www.w3schools.com/howto/img_avatar.png",
        "https://www.w3schools.com/w3css/img_avatar5.png",
        "https://image.freepik.com/free-vector/man-avatar-profile-on-round-icon_24640-14046.jpg",
        "https://www.w3schools.com/w3images/avatar6.png"
    )

    suspend fun getAllContacts(): List<Contact> = withContext(Dispatchers.IO) {
        context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )?.use {
            getContactsFromCursor(it)
        }.orEmpty()
    }

    suspend fun getContactInfo(contact: Contact): FullContact? = withContext(Dispatchers.IO) {
        context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )?.use {
            getContactFromCursor(it, contact)
        }
    }

    fun deleteContact(contact: BaseContact): Int {
        val ops = ArrayList<ContentProviderOperation>()
        ops.add(
            ContentProviderOperation.newDelete(
                ContactsContract.RawContacts.CONTENT_URI
            )
                .withSelection(
                    ContactsContract.RawContacts._ID + "=?",
                    arrayOf(contact.id.toString())
                )
                .build()
        )
        return context.contentResolver.applyBatch(ContactsContract.AUTHORITY, ops).first().count!!
    }

    fun addContact(contact: BaseContact) {
        val contactId = saveRawContact()
        saveContactName(contactId, contact.name)
        saveContactPhone(contactId, contact.numbers.first())
        if (contact is FullContact) {
            saveContactEmail(contactId, contact.emails.first())
        }
    }

    fun shareFile(fileName: String, context: Context): Intent {

        val folder = context.getExternalFilesDir("testFolder")
        val file = File(folder, fileName)

        val uri = FileProvider.getUriForFile(
            context,
            "${BuildConfig.APPLICATION_ID}.file_provider",
            file
        )

        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_STREAM, uri)
            type = context.contentResolver.getType(uri)
            setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        return Intent.createChooser(intent, null)
    }

    private fun saveRawContact(): Long {
        val uri = context.contentResolver.insert(
            ContactsContract.RawContacts.CONTENT_URI,
            ContentValues()
        )
        return uri?.lastPathSegment?.toLongOrNull() ?: 0L
    }

    private fun saveContactName(id: Long, name: String) {
        val contentValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, id)
            put(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
            )
            put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
        }
        context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues)
    }

    private fun saveContactPhone(id: Long, phone: String) {
        val contentValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, id)
            put(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
            )
            put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
        }
        context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues)
    }

    private fun saveContactEmail(id: Long, email: String) {
        val contentValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, id)
            put(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
            )
            put(ContactsContract.CommonDataKinds.Email.ADDRESS, email)
        }
        context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues)
    }

    suspend fun getFile(link: String): ResponseBody {
        return Networking.api.downloadFile(link)
    }

    private fun getContactFromCursor(cursor: Cursor, contact: Contact): FullContact? {
        if (cursor.moveToFirst().not()) return null
        return FullContact(
            contact.id,
            contact.name,
            contact.numbers,
            getEmailsForContact(contact.id),
            contact.avatar
        )
    }

    private fun getContactsFromCursor(cursor: Cursor): List<Contact> {
        if (cursor.moveToFirst().not()) return emptyList()
        val list: MutableList<Contact> = mutableListOf()
        do {
            val nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
            val name = cursor.getString(nameIndex).orEmpty()

            val idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID)
            val id = cursor.getLong(idIndex)

            list.add(
                Contact(
                    id = id,
                    name = name,
                    numbers = getPhonesForContact(id),
                    avatar = avatars.random()
                )
            )
        } while (cursor.moveToNext())
        return list
    }

    private fun getPhonesForContact(id: Long): List<String> {
        return context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
            arrayOf(id.toString()),
            null
        )?.use {
            getPhonesFromCursor(it)
        }.orEmpty()
    }

    private fun getPhonesFromCursor(cursor: Cursor): List<String> {
        if (cursor.moveToFirst().not()) return emptyList()
        val list: MutableList<String> = mutableListOf()
        do {
            val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val number = cursor.getString(numberIndex)
            list.add(number)
        } while (cursor.moveToNext())
        return list
    }

    private fun getEmailsForContact(id: Long): List<String> {
        return context.contentResolver.query(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            null,
            "${ContactsContract.CommonDataKinds.Email.CONTACT_ID} = ?",
            arrayOf(id.toString()),
            null
        )?.use {
            getEmailsFromCursor(it)
        }.orEmpty()
    }

    private fun getEmailsFromCursor(cursor: Cursor): List<String> {
        if (cursor.moveToFirst().not()) return emptyList()
        val list: MutableList<String> = mutableListOf()
        do {
            val emailIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)
            val number = cursor.getString(emailIndex)
            list.add(number)
        } while (cursor.moveToNext())
        return list
    }
}