package com.example.spaceexample.activities

import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.spaceexample.dao.SpaceDao
import com.example.spaceexample.databinding.ActivityCreateShipBinding
import com.example.spaceexample.enums.UserCreateShapeEnum
import com.example.spaceexample.models.ShipModel
import com.example.spaceexample.utils.HideActionBarActivity
import com.example.spaceexample.utils.SharedPref
import com.example.spaceexample.utils.SpaceDb
import java.util.*


class CreateShipActivity : HideActionBarActivity() {
    private var maxPoint = 15
    private var strengthEndPoint = 0
    private var speedEndPoint = 0
    private var capacityEndPoint = 0
    private var strengthStartPoint = 0
    private var speedStartPoint = 0
    private var capacityStartPoint = 0
    private lateinit var sharedPref: SharedPref

    private var myShip: ShipModel = ShipModel(null, "", 0, 0, 0)
    var pointListener: MutableLiveData<Int> =
        MutableLiveData<Int>()
    private lateinit var database: SpaceDb
    private lateinit var spaceDao: SpaceDao


    private lateinit var binding: ActivityCreateShipBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateShipBinding.inflate(layoutInflater)
        setContentView(binding.root)
        spaceDao = SpaceDb.getAppDatabase((application))?.spaceDao()!!
        sharedPref = SharedPref(this)

        clicks()
        pointListener.postValue(maxPoint)
        pointListener.observe(this, { value ->
            maxPoint = value
            binding.point.text = value.toString()
            if (maxPoint == 0) {
                when {
                    binding.strengthBar.progress == 0 -> {
                        binding.strengthBar.isClickable = false
                        binding.strengthBar.isEnabled = false
                    }
                    binding.speedBar.progress == 0 -> {
                        binding.speedBar.isClickable = false
                        binding.speedBar.isEnabled = false
                    }
                    binding.capacityBar.progress == 0 -> {
                        binding.capacityBar.isClickable = false
                        binding.capacityBar.isEnabled = false
                    }
                }
            } else {
                when {
                    binding.capacityBar.progress == 0 -> {
                        binding.capacityBar.isClickable = true
                        binding.capacityBar.isEnabled = true
                        binding.capacityBar.max = maxPoint
                    }
                    binding.speedBar.progress == 0 -> {
                        binding.speedBar.isClickable = true
                        binding.speedBar.isEnabled = true
                        binding.speedBar.max = maxPoint
                    }
                    binding.strengthBar.progress == 0 -> {
                        binding.strengthBar.isClickable = true
                        binding.strengthBar.isEnabled = true
                        binding.strengthBar.max = maxPoint
                    }
                }
            }


        })
        strengthController()
        speedController()
        capacityController()
        spaceDao.getAll().observe(this, Observer { it ->
            if (it != null) {
                for (item in it) {
                    print(item.name)
                }
            }
        })
    }

    private fun clicks() {
        binding.createShip.setOnClickListener {
            isUserDataValid()
        }
    }

    private fun isUserDataValid() {
        when {
            binding.shipName.text.isNullOrBlank() -> {
                Toast.makeText(
                    this@CreateShipActivity,
                    "Geminizin Adını Belirleyin",
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.strengthBar.progress == 0 -> {
                Toast.makeText(
                    this@CreateShipActivity,
                    "Dayanıklılık 0 olamaz",
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.speedBar.progress == 0 -> {
                Toast.makeText(
                    this@CreateShipActivity,
                    "Hız 0 olamaz",
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.capacityBar.progress == 0 -> {
                Toast.makeText(
                    this@CreateShipActivity,
                    "Kapasite 0 olamaz",
                    Toast.LENGTH_SHORT
                ).show()
            }
            maxPoint > 0 -> {
                Toast.makeText(
                    this@CreateShipActivity,
                    "Puanlarınızın Hepsini Dağıtın",
                    Toast.LENGTH_SHORT
                ).show()
            }
            maxPoint < 0 -> {
                Toast.makeText(
                    this@CreateShipActivity,
                    "Kullanılabilir 15 puanınız vardır.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {
                myShip.speed = binding.speedBar.progress
                myShip.capacity = binding.capacityBar.progress
                myShip.strength = binding.strengthBar.progress
                myShip.name = binding.shipName.text.toString()
                insert(myShip)

            }
        }
    }

    private fun strengthController() {
        if (maxPoint > 0) {
            with(binding) {
                strengthBar.setOnSeekBarChangeListener(object :
                    SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seek: SeekBar,
                        progress: Int, fromUser: Boolean
                    ) {
                        if (progress > 0 && maxPoint > 0) {
                            pointListener.postValue(minusTotalPoint(progress - seek.progress))

                        }
                        strengthText.text = "Dayanıklılık :" + "${seek.progress}"
                    }

                    override fun onStartTrackingTouch(seek: SeekBar) {
                        strengthStartPoint = seek.progress

                    }

                    override fun onStopTrackingTouch(seek: SeekBar) {
                        strengthEndPoint = seek.progress

                        if (maxPoint == 0 && strengthEndPoint - strengthStartPoint < 0) {
                            pointListener.postValue(minusTotalPoint(strengthEndPoint - strengthStartPoint))
                            strengthBar.isClickable = true

                        } else if (maxPoint <= 0 && strengthEndPoint - strengthStartPoint > 0) {
                            strengthBar.isClickable = false
                        } else {
                            pointListener.postValue(minusTotalPoint(strengthEndPoint - strengthStartPoint))

                        }


                    }
                })
            }
        }

    }

    private fun speedController() {
        if (maxPoint > 0) {
            with(binding) {
                speedBar.setOnSeekBarChangeListener(object :
                    SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seek: SeekBar,
                        progress: Int, fromUser: Boolean
                    ) {
                        speedText.text = "Hız :" + "${seek.progress}"
                    }

                    override fun onStartTrackingTouch(seek: SeekBar) {
                        speedStartPoint = seek.progress

                    }

                    override fun onStopTrackingTouch(seek: SeekBar) {
                        speedEndPoint = seek.progress
                        if (maxPoint == 0 && speedEndPoint - speedStartPoint < 0) {
                            pointListener.postValue(minusTotalPoint(speedEndPoint - speedStartPoint))
                            speedBar.isClickable = true

                        } else if (maxPoint <= 0 && speedEndPoint - speedStartPoint > 0) {
                            speedBar.isClickable = false
                        } else {
                            pointListener.postValue(minusTotalPoint(speedEndPoint - speedStartPoint))

                        }

                    }
                })
            }
        }

    }

    private fun capacityController() {
        if (maxPoint > 0) {
            with(binding) {
                capacityBar.setOnSeekBarChangeListener(object :
                    SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seek: SeekBar,
                        progress: Int, fromUser: Boolean
                    ) {

                        capacityText.text = "Kapasite :" + "${seek.progress}"
                    }

                    override fun onStartTrackingTouch(seek: SeekBar) {
                        capacityStartPoint = seek.progress

                    }

                    override fun onStopTrackingTouch(seek: SeekBar) {
                        capacityEndPoint = seek.progress
                        if (maxPoint == 0 && capacityEndPoint - capacityStartPoint < 0) {
                            pointListener.postValue(minusTotalPoint(capacityEndPoint - capacityStartPoint))
                            capacityBar.isClickable = true

                        } else if (maxPoint == 0 && capacityEndPoint - capacityStartPoint > 0) {
                            capacityBar.isClickable = false
                        } else {
                            pointListener.postValue(minusTotalPoint(capacityEndPoint - capacityStartPoint))

                        }

                    }
                })
            }
        }

    }


    private fun insert(ship: ShipModel) {

        spaceDao.insert(ship)
        sharedPref.setUserCreateSpaceShip(UserCreateShapeEnum.CREATED.name)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun minusTotalPoint(lastPoint: Int): Int {
        return maxPoint - lastPoint
    }
}