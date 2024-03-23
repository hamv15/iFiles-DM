package com.hamv.ifilesdm

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.hamv.ifilesdm.databinding.ActivityMainBinding
import com.hamv.ifilesdm.model.Car
import com.hamv.ifilesdm.model.Student
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //Propiedades para manejar persistencia de preferencias
    private lateinit var sp: SharedPreferences
    private lateinit var spEditor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Instanciando las shared preferences con su editor
        sp = getPreferences(Context.MODE_PRIVATE)
        spEditor = sp.edit()

        val car = sp.getString("mi_carro", null)
        car?.let {
            Toast.makeText(this, "El carro almacenado es $car", Toast.LENGTH_SHORT).show()
        }

    }

    fun clickSave(view: View) {
        view.hideKeyboard()
        if (binding.tietText.text.toString().isNotEmpty()){

            //val student = Student(name = binding.tietText.text.toString())
            val car = Car(
                marca = binding.tietText.text.toString(),
                modelo = "EcoSport",
                anio = 2018,
                numeroSerie = "MAJBD1334JD2341S"
                )

            val bytesToSave = Gson().toJson(car).encodeToByteArray()




            //Codificando a bytes la cadena de texto a almacenar
            //val bytesToSave = binding.tietText.text.toString().encodeToByteArray()
            try {
                val file = File(filesDir, "mi_archivo.txt")
                if (!file.exists()){
                    file.createNewFile()
                }
                val fos = FileOutputStream(file, false)
                fos.write(bytesToSave)
                fos.close()
                //guardar persistencia
                spEditor.putString("mi_carro",bytesToSave.decodeToString()).apply()
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
                //binding.tvContent.text = file.readBytes().decodeToString()

                //Para Json
                val jsonString = file.readBytes().decodeToString()
                //val student = Gson().fromJson(jsonString, Student::class.java)
                val car = Gson().fromJson(jsonString, Car::class.java)
                //binding.tvContent.text = "Id: ${student.id}, Nombre: ${student.name}"
                //Forma correcta
                //binding.tvContent.text = getString(R.string.student, student.id.toString(), student.name)
                binding.tvContent.text = getString(
                    R.string.car,
                    car.id.toString(),
                    car.marca,
                    car.modelo,
                    car.anio.toString(),
                    car.numeroSerie
                )
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