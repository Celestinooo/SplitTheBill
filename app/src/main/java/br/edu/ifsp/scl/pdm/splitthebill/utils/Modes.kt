package br.edu.ifsp.scl.pdm.splitthebill.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize enum class Modes : Parcelable {
    INSERT,UPDATE,REMOVE
}