package com.example.sqlitecrud

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.IntegerRes
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Modifier

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editTextId = findViewById(R.id.editTextId) as EditText
        val editTextName = findViewById(R.id.editTextName) as EditText
        val editTextEmail = findViewById(R.id.editTextEmail)as EditText
        val editTextAge = findViewById(R.id.editTextAge)as EditText
        val listView = findViewById(R.id.listView)as ListView
        val buttonSave = findViewById(R.id.buttonSave)as Button
        val buttonEdit = findViewById(R.id.buttonEdit)as Button
        val buttonView = findViewById(R.id.buttonView)as Button
        val buttonDelete = findViewById(R.id.buttonDelete)as Button

        buttonDelete.setOnClickListener {
            deleteRecord()
        }

        buttonEdit.setOnClickListener {
            updateRecord()
        }
        buttonSave.setOnClickListener {
            saveRecord()
        }
        buttonView.setOnClickListener {
            viewRecord()
        }


    }

//    finish()
//    val intent = Intent(this,MainActivity::class.java)
//    startActivity(intent)

    //method for saving records in database
    fun saveRecord(){
        val id = editTextId.text.toString()
        val name = editTextName.text.toString()
        val email = editTextEmail.text.toString()
        val age = editTextAge.text.toString()
        val databaseHandler: DatabaseHelper= DatabaseHelper(this)
        if(name.trim()!="" && email.trim()!=""){
            val status = databaseHandler.insertData(DataModel(Integer.parseInt(id),name, email,Integer.parseInt(age)))
            if(status > -1){
                viewRecord()
                Toast.makeText(applicationContext,"record save",Toast.LENGTH_LONG).show()
                editTextId.text.clear()
                editTextName.text.clear()
                editTextEmail.text.clear()
                editTextAge.text.clear()
            }
        }else{
            Toast.makeText(applicationContext,"id or name or email cannot be blank",Toast.LENGTH_LONG).show()
        }

    }

    fun viewRecord(){
        //creating the instance of DatabaseHandler class
        val databaseHelper: DatabaseHelper= DatabaseHelper(this)
        //calling the viewEmployee method of DatabaseHepler class to read the records
        val data: List<DataModel> = databaseHelper.viewData()
        val dataArrayId = Array<String>(data.size){"0"}
        val dataArrayName = Array<String>(data.size){"null"}
        val dataArrayEmail = Array<String>(data.size){"null"}
        val dataArrayAge = Array<String>(data.size){"null"}
        var index = 0
        for(e in data){
            dataArrayId[index] = e.userId.toString()
            dataArrayName[index] = e.userName
            dataArrayEmail[index] = e.userEmail
            dataArrayAge[index] = e.userAge.toString()
            index++
        }
        //creating custom ArrayAdapter
        val myListAdapter = MyListAdapter(this,dataArrayId,dataArrayName,dataArrayEmail,dataArrayAge)
        listView.adapter = myListAdapter
    }


    //method for updating records based on user id
    fun updateRecord(){
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.update_dialog, null)
        dialogBuilder.setView(dialogView)

        val edtId = dialogView.findViewById(R.id.updateId) as EditText
        val edtName = dialogView.findViewById(R.id.updateName) as EditText
        val edtEmail = dialogView.findViewById(R.id.updateEmail) as EditText
        val edtAge = dialogView.findViewById(R.id.updateAge) as EditText

        dialogBuilder.setTitle("Update Record")
        dialogBuilder.setMessage("Enter data below")
        dialogBuilder.setPositiveButton("Update", DialogInterface.OnClickListener { _, _ ->

            val updateId = edtId.text.toString()
            val updateName = edtName.text.toString()
            val updateEmail = edtEmail.text.toString()
            val updateAge = edtAge.text.toString()
            //creating the instance of DatabaseHandler class
            val databaseHelper: DatabaseHelper= DatabaseHelper(this)
            if(updateId.trim()!="" && updateName.trim()!="" && updateEmail.trim()!=""){
                //calling the updateEmployee method of DatabaseHandler class to update record
                val status = databaseHelper.updateData(DataModel(Integer.parseInt(updateId),updateName, updateEmail,Integer.parseInt(updateAge)))
                if(status > -1){
                    viewRecord()
                    Toast.makeText(applicationContext,"record update",Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(applicationContext,"id or name or email cannot be blank",Toast.LENGTH_LONG).show()
            }

        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
            //pass
        })
        val b = dialogBuilder.create()
        b.show()
    }


    //method for deleting records based on id
    fun deleteRecord(){
        //creating AlertDialog for taking user id
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.delete_dialog, null)
        dialogBuilder.setView(dialogView)

        val dltId = dialogView.findViewById(R.id.deleteId) as EditText
        dialogBuilder.setTitle("Delete Record")
        dialogBuilder.setMessage("Enter id below")
        dialogBuilder.setPositiveButton("Delete", DialogInterface.OnClickListener { _, _ ->

            val deleteId = dltId.text.toString()
            //creating the instance of DatabaseHandler class
            val databaseHelper: DatabaseHelper= DatabaseHelper(this)
            if(deleteId.trim()!=""){
                //calling the deleteEmployee method of DatabaseHandler class to delete record
                val status = databaseHelper.deleteData(DataModel(Integer.parseInt(deleteId),"","",0))
                if(status > -1){
                    viewRecord()
                    Toast.makeText(applicationContext,"record deleted",Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(applicationContext,"id or name or email cannot be blank",Toast.LENGTH_LONG).show()
            }

        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { _, _ ->
            //pass
        })
        val b = dialogBuilder.create()
        b.show()
    }



}
