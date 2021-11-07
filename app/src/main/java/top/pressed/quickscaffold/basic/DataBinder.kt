package top.pressed.quickscaffold.basic

interface DataBinder<DataType> {
    fun onBind(item: DataType)
}