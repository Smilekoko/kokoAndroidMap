package com.shiki.koko.base.file

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter


//PrintWriter当管道满了才会写入数据，否则只有主动调用flush之后写入数据。当调用close首先会调用依次flush，然后关闭管道。
inline fun File.print(crossinline block: PrintWriter.() -> Unit) {
    PrintWriter(BufferedWriter(FileWriter(this))).apply(block).close()
}