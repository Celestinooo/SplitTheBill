package br.edu.ifsp.scl.pdm.splitthebill.controller

import br.edu.ifsp.scl.pdm.splitthebill.model.Member

class MembersController {
    val memberList: ArrayList<Member> = arrayListOf()

    fun insert(member: Member) {
        memberList.add(member)
    }

    fun update(member: Member): Int? {
        val index = getMemberIndex(member) ?: return null
        memberList[index] = member
        return index
    }

    fun remove(member: Member) : Int? {
        val index = getMemberIndex(member) ?: return null
        memberList.removeAt(index)
        return index
    }

    private fun getMemberIndex(member: Member) : Int? {
        memberList.forEachIndexed{ i, m ->
            if(m.id == member.id) return i
        }
        return null
    }

}