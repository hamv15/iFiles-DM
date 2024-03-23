package com.hamv.ifilesdm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hamv.ifilesdm.databinding.ActivityMainBinding
import com.hamv.ifilesdm.model.Student
import org.simpleframework.xml.core.Persister
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.StringWriter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //Para poder serializar xml
    private  lateinit var  serializer: Persister

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Instanciado el serializador de xml
        serializer = Persister()


    }

    fun clickSave(view: View) {
        view.hideKeyboard()
        if (binding.tietText.text.toString().isNotEmpty()){

            //Generar objeto student
            val student = Student(name = binding.tietText.text.toString())

            //Creamos un escritor para el xml
            val writer = StringWriter()

            //Serializamos el objeto a XML
            serializer.write(student, writer)

            //Obtenemos el xml como cadena
            val xmlString =writer.toString()

            val bytesToSave = xmlString.encodeToByteArray()

            try {
                val file = File(filesDir, "mi_archivo.txt")
                if (!file.exists()){
                    file.createNewFile()
                }

                //Otra opcion
                file.writeBytes(bytesToSave)

                sbMessage(
                    binding.clRoot,
                    "La informacion fue almacenada correctamente",
                    bgColor = "#50C228"
                )
            }catch (e: Exception){
                toastMessage("Error al guardado")
            }

        }else{
            sbMessage(
                binding.clRoot,
                "Ingrese la información a almacenar"
            )
            binding.tietText.error = "No puede estar vacío"
            binding.tietText.requestFocus()
        }
    }
    fun clickRead(view: View) {
        view.hideKeyboard()
        try {
            val file = File(filesDir, "mi_archivo.txt")
            if (file.exists()){
                val xmlString = file.readBytes().decodeToString()

                val student = serializer.read(Student::class.java, xmlString)
                binding.tvContent.text = getString(R.string.student, student.id.toString(), student.name)

            }else{
                sbMessage(
                    binding.clRoot,
                    "No existe informacion o archivo almacenado en el dispositivo"
                )
            }
        }catch (e:Exception){
            //manejo de la excepcion
        }
    }

}