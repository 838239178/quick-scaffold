package top.pressed.quickscaffold.core

interface DataBinder<DataType> {
    fun onBind(item: DataType)
}