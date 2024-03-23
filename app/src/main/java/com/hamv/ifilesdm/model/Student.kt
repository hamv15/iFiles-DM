package com.hamv.ifilesdm.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "Student")
data class Student(
    @field:Element(name = "id")
    var id: Int = System.currentTimeMillis().toInt(), //Va a generar un id único cada vez
    @field:Element(name = "name")
    var name: String? = null
)
