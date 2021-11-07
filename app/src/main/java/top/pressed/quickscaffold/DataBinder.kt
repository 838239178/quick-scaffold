package top.pressed.quickscaffold

interface DataBinder<DataType> {
    fun onBind(item: DataType)
}