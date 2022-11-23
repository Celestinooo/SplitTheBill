package br.edu.ifsp.scl.pdm.splitthebill.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Member(
    val id: Int,
    val name: String,
    val totalPaid: Double,
    val itemsBought: String
) : Parcelable {
    fun mustPay(totalPerMember : Double) : Double {
        return if(totalPaid >= totalPerMember) 0.0
        else totalPerMember - totalPaid
    }
    fun mustReceive(totalPerMember: Double) : Double {
        return if(totalPaid < totalPerMember) 0.0
        else totalPaid - totalPerMember
    }
}