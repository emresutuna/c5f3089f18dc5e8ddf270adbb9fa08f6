package com.example.spaceexample.models

import com.google.gson.annotations.SerializedName

open  class Ships(
    @SerializedName("name") var name: String? = null,
    @SerializedName("coordinateX") var coordinateX: Int? = null,
    @SerializedName("coordinateY") var coordinateY: Int? = null,
    @SerializedName("capacity") var capacity: Int? = null,
    @SerializedName("stock") var stock: Int? = null,
    @SerializedName("need") var need: Int? = null
) {

}