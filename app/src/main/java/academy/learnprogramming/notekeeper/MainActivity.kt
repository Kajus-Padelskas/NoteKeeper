package academy.learnprogramming.notekeeper

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private val tag = "MainActivity"
    private var notePosition = POSITION_NOT_SET

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val adapterCourses = ArrayAdapter<CourseInfo>(this, android.R.layout.simple_spinner_item, DataManager.courses.values.toList())
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.spinnerCourses).adapter = adapterCourses // Setting up adapter for courses drop down view (Spinner)

        notePosition = savedInstanceState?.getInt(NODE_POSITION, POSITION_NOT_SET) ?:
                intent.getIntExtra(NODE_POSITION, POSITION_NOT_SET) // Getting note Position from another activity

        if(notePosition != POSITION_NOT_SET)
            displayNote() // Displaying note content
        else {
            DataManager.notes.add(NoteInfo()) // Creating new note with default arguments null
            notePosition = DataManager.notes.lastIndex // Setting position of new NoteInfo
        }
        Log.d(tag, "onCreate")
    }

    private fun displayNote() {
        Log.i(tag, "Displaying note for position: $notePosition")
        val note = DataManager.notes[notePosition]
        findViewById<EditText>(R.id.textNoteTitle).setText(note.title)
        findViewById<EditText>(R.id.textNoteText).setText(note.text)

        val coursePosition = DataManager.courses.values.indexOf(note.course)
        findViewById<Spinner>(R.id.spinnerCourses).setSelection(coursePosition)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_next -> {
                moveNext()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun moveNext() {
        ++notePosition
        displayNote()
        invalidateOptionsMenu()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        if(notePosition >= DataManager.notes.lastIndex){
            val menuItem = menu?.findItem(R.id.action_next)
            if(menuItem != null){
                menuItem.icon = getDrawable(R.drawable.ic_white_block_24dp) // changing icon
                menuItem.isEnabled = false // Setting to not clickable
            }
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onPause() {
        super.onPause()
        saveNote()
        Log.d(tag, "onPause")
    }

    private fun saveNote() {
        val note = DataManager.notes[notePosition]
        note.title = findViewById<TextView>(R.id.textNoteTitle).text.toString()
        note.text = findViewById<TextView>(R.id.textNoteText).text.toString()
        note.course = findViewById<Spinner>(R.id.spinnerCourses).selectedItem as CourseInfo
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(NODE_POSITION, notePosition)
    }

}