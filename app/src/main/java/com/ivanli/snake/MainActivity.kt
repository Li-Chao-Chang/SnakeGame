package com.ivanli.snake

import android.content.DialogInterface
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val viewModel = ViewModelProvider(this).get(SnakeViewModel::class.java)
        viewModel.body.observe(this, Observer {
            view_game.snakeBody= it
            view_game.invalidate()
        })
        viewModel.gameState.observe(this, Observer {
            if(it == GameState.GAMEOVER){
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("GameOver")
                    .setMessage("Game Over")
                    .setPositiveButton("Reset") { dialog, which ->
                        view_game.invalidate()
                        viewModel.reset()
                    }
                    .show()
            }
        })
        viewModel.apple.observe(this,Observer{
            view_game.apple = it
            view_game.invalidate()
        })
        viewModel.score.observe(this,Observer{
            tv_score.setText(it.toString())
        })
        viewModel.start()

        im_up.setOnClickListener{ viewModel.move(Direction.UP) }
        im_down.setOnClickListener{ viewModel.move(Direction.DOWN) }
        im_left.setOnClickListener{ viewModel.move(Direction.LEFT) }
        im_right.setOnClickListener{ viewModel.move(Direction.RIGHT) }
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
            else -> super.onOptionsItemSelected(item)
        }
    }
}