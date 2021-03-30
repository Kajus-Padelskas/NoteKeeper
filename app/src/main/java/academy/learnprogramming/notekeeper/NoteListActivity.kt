package academy.learnprogramming.notekeeper

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity

class NoteListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val activityIntent = Intent(this, MainActivity::class.java)
            startActivity(activityIntent)
        }

        findViewById<ListView>(R.id.listNotes).adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, DataManager.notes);

        findViewById<ListView>(R.id.listNotes).setOnItemClickListener {parent, view, position, id ->
            val activityIntent = Intent(this, MainActivity::class.java)
            activityIntent.putExtra(NODE_POSITION, position)
            startActivity(activityIntent)
        }

    }

    override fun onResume() {
        super.onResume()
        (findViewById<ListView>(R.id.listNotes).adapter as ArrayAdapter<*>).notifyDataSetChanged()
    }
}