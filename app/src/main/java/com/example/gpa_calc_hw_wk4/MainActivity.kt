package com.example.gpa_calc_hw_wk4

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var classGradeOne: EditText
    private lateinit var classGradeTwo: EditText
    private lateinit var classGradeThree: EditText
    private lateinit var classGradeFour: EditText
    private lateinit var classGradeFive: EditText

    private lateinit var computeButton: Button

    private lateinit var displayGPA: TextView

    private var currentBackgroundColor: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //initializing class grade TextFields
        classGradeOne = findViewById(R.id.class_one_gpa)
        classGradeTwo = findViewById(R.id.class_two_gpa)
        classGradeThree = findViewById(R.id.class_three_gpa)
        classGradeFour = findViewById(R.id.class_four_gpa)
        classGradeFive = findViewById(R.id.class_five_gpa)

        //initializing button
        computeButton = findViewById(R.id.buttonText)


        //Initializing displaying grade
        displayGPA = findViewById(R.id.gpa_result)

        //restore saved state if it exists
        if (savedInstanceState != null) {
            classGradeOne.setText(savedInstanceState.getString("grade1", ""))
            classGradeTwo.setText(savedInstanceState.getString("grade2", ""))
            classGradeThree.setText(savedInstanceState.getString("grade3", ""))
            classGradeFour.setText(savedInstanceState.getString("grade4", ""))
            classGradeFive.setText(savedInstanceState.getString("grade5", ""))
            displayGPA.text = savedInstanceState.getString("gpaResult", getString(R.string.displayGPA_default))
            computeButton.setText(savedInstanceState.getString("buttonText", getString(R.string.compute_gpa)))

            currentBackgroundColor = savedInstanceState.getInt("backgroundColor", resources.getColor(R.color.white))
            findViewById<View>(R.id.main).setBackgroundColor(currentBackgroundColor)
        } else {
            currentBackgroundColor = resources.getColor(R.color.white)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        //save all grade inputs
        outState.putString("grade1", classGradeOne.text.toString())
        outState.putString("grade2", classGradeTwo.text.toString())
        outState.putString("grade3", classGradeThree.text.toString())
        outState.putString("grade4", classGradeFour.text.toString())
        outState.putString("grade5", classGradeFive.text.toString())

        //save displayed GPA result
        outState.putString("gpaResult", displayGPA.text.toString())

        //save button text
        outState.putString("buttonText", computeButton.text.toString())

        //save background color
        outState.putInt("backgroundColor", currentBackgroundColor)
    }

    /**
     * onHandle function that can compute or clear the form
     */
    fun onHandleComputeButton(view: View) {

        //if the button text says "Clear Form", the whole form will be cleared
        if(computeButton.text.equals("Clear Form")){
            return clearForm()
        }

        //getting user's grades, if there is an empty TextField, it will be highlighted red
        val grade1 = classGradeOne.text.toString().toDoubleOrNull() ?: return classGradeOne.setBackgroundResource(R.drawable.edittext_border_error)
        val grade2 = classGradeTwo.text.toString().toDoubleOrNull() ?: return classGradeTwo.setBackgroundResource(R.drawable.edittext_border_error)
        val grade3 = classGradeThree.text.toString().toDoubleOrNull() ?: return classGradeThree.setBackgroundResource(R.drawable.edittext_border_error)
        val grade4 = classGradeFour.text.toString().toDoubleOrNull() ?: return classGradeFour.setBackgroundResource(R.drawable.edittext_border_error)
        val grade5 = classGradeFive.text.toString().toDoubleOrNull() ?: return classGradeFive.setBackgroundResource(R.drawable.edittext_border_error)


        //calculating the user's gpa
        val gpa = (grade1 + grade2 + grade3 + grade4 + grade5) / 5.0


        //changing background depending on the user's GPA
        if(gpa < 60) {
            //changes the background to red if gpa is < 60
            currentBackgroundColor = resources.getColor(R.color.red_bg)
            findViewById<View>(R.id.main).setBackgroundColor(currentBackgroundColor)
        }
        else if ((gpa >= 61) and (gpa <= 79)) {
            //changes the background to yellow if gpa is >= 61 and gpa <= 79
            currentBackgroundColor = resources.getColor(R.color.yellow_bg)
            findViewById<View>(R.id.main).setBackgroundColor(currentBackgroundColor)
        }
        else if ((gpa >= 80) and (gpa <= 100)){
            //changes the background to green if gpa >= 80 and gpa <= 100
            currentBackgroundColor = resources.getColor(R.color.green_bg)
            findViewById<View>(R.id.main).setBackgroundColor(currentBackgroundColor)
        }

        //gets the displayGPA R.string and concatenates with the gpa
        val totalGPA = getString(R.string.displayGPA, gpa)


        //displays the user's final gpa and compute button gets changed to "Clear Form"
        displayGPA.text = totalGPA
        computeButton.setText(R.string.clear_form)

        //clears any TextFields that were highlighted red
        clearRedTextFields()

    }

    /**
     * Function that clears the whole form
     */
    fun clearForm() {
        //clearing all TextFields
        classGradeOne.text.clear()
        classGradeTwo.text.clear()
        classGradeThree.text.clear()
        classGradeFour.text.clear()
        classGradeFive.text.clear()
        displayGPA.text = getString(R.string.displayGPA_default)

        //resetting EditText borders to normal
        classGradeOne.setBackgroundResource(R.drawable.edittext_border)
        classGradeTwo.setBackgroundResource(R.drawable.edittext_border)
        classGradeThree.setBackgroundResource(R.drawable.edittext_border)
        classGradeFour.setBackgroundResource(R.drawable.edittext_border)
        classGradeFive.setBackgroundResource(R.drawable.edittext_border)

        //changing the button text to "Compute GPA"
        computeButton.setText(R.string.compute_gpa)

        //changing the background back to white
        currentBackgroundColor = resources.getColor(R.color.white)
        findViewById<View>(R.id.main).setBackgroundColor(currentBackgroundColor)
    }


    /**
     * clears any red highlightings in the textfields if there are any present
     */
    fun clearRedTextFields() {
        classGradeOne.setBackgroundResource(R.drawable.edittext_border)
        classGradeTwo.setBackgroundResource(R.drawable.edittext_border)
        classGradeThree.setBackgroundResource(R.drawable.edittext_border)
        classGradeFour.setBackgroundResource(R.drawable.edittext_border)
        classGradeFive.setBackgroundResource(R.drawable.edittext_border)
    }
}




