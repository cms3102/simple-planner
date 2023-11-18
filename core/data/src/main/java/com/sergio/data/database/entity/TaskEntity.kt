package com.sergio.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.challenge.model.Task

@Entity(tableName = TaskEntity.NAME)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    @ColumnInfo("due_date")
    val dueDate: String,
    @ColumnInfo("due_date_original")
    val dueDateOriginal: Long? = null,
    val complete: Boolean = false,
) {
    companion object {
        const val NAME = "task"
    }
}

internal fun TaskEntity.toDomain(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        dueDate = dueDate,
        complete = complete
    )
}