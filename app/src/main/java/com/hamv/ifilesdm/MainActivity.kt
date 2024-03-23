package com.hamv.ifilesdm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hamv.ifilesdm.databinding.ActivityMainBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    fun clickSave(view: View) {
        view.hideKeyboard()
        if (binding.tietText.text.toString().isNotEmpty()){
            //Codificando a bytes la cadena de texto a almacenar
            val bytesToSave = binding.tietText.text.toString().encodeToByteArray()
            try {
                val file = File(filesDir, "mi_archivo.txt")
                if (!file.exists()){
                    file.createNewFile()
                }
                val fos = FileOutputStream(file, true)
                fos.write(bytesToSave)
                fos.close()

                //Otra opcion
                //file.writeBytes(bytesToSave)
                //file.appendBytes(bytesToSave)

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
                /*val fis = FileInputStream(file)
                val content = fis.readBytes()
                binding.tvContent.text = content.decodeToString()
                fis.close()*/

                binding.tvContent.text = file.readBytes().decodeToString()

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