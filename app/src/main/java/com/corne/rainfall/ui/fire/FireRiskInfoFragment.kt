package com.corne.rainfall.ui.fire

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.corne.rainfall.R


class fireRiskInfoFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fire_risk_info, container, false)

        //Doesnt quite work, donno why
        //Only first card (article1card) works, but links to the third card's link

        val a1 = view.findViewById<CardView>(R.id.article1Card)
        a1.setOnClickListener {
            val intent = Intent()
            intent.setAction(Intent.ACTION_VIEW)
            intent.addCategory(Intent.CATEGORY_BROWSABLE)
            intent.setData(Uri.parse("https://esc.rutgers.edu/fact_sheet/fire-prevention-and-safety-measures-around-the-farm/"))
            startActivity(intent)
        }

        val a2 = view.findViewById<CardView>(R.id.article2Card)
        a1.setOnClickListener {
            val intent = Intent()
            intent.setAction(Intent.ACTION_VIEW)
            intent.addCategory(Intent.CATEGORY_BROWSABLE)
            intent.setData(Uri.parse("https://www.sciencelearn.org.nz/image_maps/49-rural-fire-risk"))
            startActivity(intent)
        }

        val a3 = view.findViewById<CardView>(R.id.article3Card)
        a1.setOnClickListener {
            val intent = Intent()
            intent.setAction(Intent.ACTION_VIEW)
            intent.addCategory(Intent.CATEGORY_BROWSABLE)
            intent.setData(Uri.parse("https://www.weathersa.co.za/home/fireindex"))
            startActivity(intent)
        }

        return view
    }
}