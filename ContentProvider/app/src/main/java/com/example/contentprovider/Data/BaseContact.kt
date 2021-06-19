package com.example.contentprovider.Data

abstract class BaseContact(open val id: Long, open val name: String, open val numbers: List<String>, open val avatar: String)