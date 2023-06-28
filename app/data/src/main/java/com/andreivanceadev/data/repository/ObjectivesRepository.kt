package com.andreivanceadev.data.repository

import com.andreivanceadev.data.datamodel.ObjectiveDM

interface ObjectivesRepository {
    suspend fun getObjectives(): List<ObjectiveDM>
    suspend fun addObjective(objective: ObjectiveDM)
}