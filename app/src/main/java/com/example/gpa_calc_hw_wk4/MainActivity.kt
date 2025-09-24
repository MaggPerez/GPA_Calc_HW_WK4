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
        val grade1 = classGradeOne.text.toString().toDoubleOrNull() ?: return classGradeOne.setBackgroundColor(resources.getColor(R.color.red_bg))
        val grade2 = classGradeTwo.text.toString().toDoubleOrNull() ?: return classGradeTwo.setBackgroundColor(resources.getColor(R.color.red_bg))
        val grade3 = classGradeThree.text.toString().toDoubleOrNull() ?: return classGradeThree.setBackgroundColor(resources.getColor(R.color.red_bg))
        val grade4 = classGradeFour.text.toString().toDoubleOrNull() ?: return classGradeFour.setBackgroundColor(resources.getColor(R.color.red_bg))
        val grade5 = classGradeFive.text.toString().toDoubleOrNull() ?: return classGradeFive.setBackgroundColor(resources.getColor(R.color.red_bg))


        //calculating the user's gpa
        val gpa = (grade1 + grade2 + grade3 + grade4 + grade5) / 5.0


        //changing background depending on the user's GPA
        if(gpa < 60) {
            //changes the background to red if gpa is < 60
            findViewById<View>(R.id.main).setBackgroundColor(resources.getColor(R.color.red_bg))
        }
        else if ((gpa >= 61) and (gpa <= 79)) {
            //changes the background to yellow if gpa is >= 61 and gpa <= 79
            findViewById<View>(R.id.main).setBackgroundColor(resources.getColor(R.color.yellow_bg))
        }
        else if ((gpa >= 80) and (gpa <= 100)){
            //changes the background to green if gpa >= 80 and gpa <= 100
            findViewById<View>(R.id.main).setBackgroundColor(resources.getColor(R.color.green_bg))
        }

        //gets the displayGPA R.string and concatenates with the gpa
        val totalGPA = getString(R.string.displayGPA, gpa)


        //displays the user's final gpa and compute button gets changed to "Clear Form"
        displayGPA.text = totalGPA
        computeButton.setText(R.string.clear_form)

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

        //changing the button text to "Compute GPA"
        computeButton.setText(R.string.compute_gpa)

        //changing the background back to white
        findViewById<View>(R.id.main).setBackgroundColor(resources.getColor(R.color.white))
    }
}




