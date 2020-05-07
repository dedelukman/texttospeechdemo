package com.abahstudio.texttospeechdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.math.log

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var tts : TextToSpeech?=null // variable for TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the Text to Speech
        tts = TextToSpeech(this, this)

        btnSpeak.setOnClickListener { view ->

            if (etEnteredText.text.isEmpty()){
                Toast.makeText(this@MainActivity, "Enter a text to speech", Toast.LENGTH_LONG).show()
            }else{
                speakOut(etEnteredText.text.toString())
            }
        }
    }

    private fun speakOut(toString: String) {
        tts!!.speak(toString, TextToSpeech.QUEUE_FLUSH, null)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            //set US English as language for tts
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "The Language specified is not supported")
            }
        }else{
            Log.e("TTS", "Initialization Failed!")
        }
    }

    public override fun onDestroy() {
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}
