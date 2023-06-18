package com.example.firebasemusicplayer.view.fragment.admin

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.databinding.FragmentOutputFileExcelBinding
import com.example.firebasemusicplayer.model.data.RealtimeDatabaseHelper
import com.example.firebasemusicplayer.model.entity.Music
import com.facebook.FacebookSdk
import com.facebook.FacebookSdk.getApplicationContext
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Workbook
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.ArrayList


class OutputFileExcelFragment : Fragment() {

    private lateinit var binding : FragmentOutputFileExcelBinding
    private var musicList: ArrayList<Music>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_output_file_excel, container, false)

        binding.imgBtnBack.setOnClickListener{
            findNavController().navigate(R.id.action_outputFileExcelFragment_to_adminFragment)
        }

        musicList = ArrayList<Music>()

        RealtimeDatabaseHelper.getAllSongsFromFirebase(onSuccess = { musicList ->
            this.musicList?.clear()
            this.musicList?.addAll(musicList)

            Log.d("ASS", "arrayList" + musicList!!.size)
            binding.imgBtnXls.setOnClickListener{

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                        if (getApplicationContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                            val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            requestPermissions(permissions, 1)
                    } else {
                        //get data from edit text
//                            userList = ArrayList()
//                            userList?.add(Music(1, "a", "b", "1", "1"))
//                            userList?.add(Music(2, "a", "b", "2", "2"))
//                            userList?.add(Music(3, "a", "b", "3", "3"))
//                            userList?.add(Music(4, "a", "b", "4", "4"))
//                            userList?.add(Music(5, "a", "b", "5", "5"))
//                            userList?.add(Music(6, "a", "b", "6", "6"))
//                            userList?.add(Music(7, "a", "b", "7", "7"))
//                            userList?.add(Music(8, "a", "b", "8", "8"))

                        if (musicList!!.isNotEmpty()) {
                            val wb: Workbook = HSSFWorkbook()
                            var cell: Cell
                            val sheet = wb.createSheet("Demo Excel Sheet")
                            val row = sheet.createRow(0)

                            cell = row.createCell(0)
                            cell.setCellValue("id")

                            cell = row.createCell(1)
                            cell.setCellValue("imageURL")

                            cell = row.createCell(2)
                            cell.setCellValue("singerName")

                            cell = row.createCell(3)
                            cell.setCellValue("songName")

                            cell = row.createCell(4)
                            cell.setCellValue("songURL")

                            sheet.setColumnWidth(0, 20 * 100)
                            sheet.setColumnWidth(1, 30 * 200)
                            sheet.setColumnWidth(2, 30 * 200)
                            sheet.setColumnWidth(3, 30 * 200)
                            sheet.setColumnWidth(4, 30 * 100)

                            for (userModel in musicList!!) {
                                val row1 = sheet.createRow(musicList!!.indexOf(userModel) + 1)
                                cell = row1.createCell(0)
                                cell.setCellValue(userModel.id.toString())
                                cell = row1.createCell(1)
                                cell.setCellValue(userModel.imageURL)
                                cell = row1.createCell(2)
                                cell.setCellValue(userModel.singerName)
                                cell = row1.createCell(3)
                                cell.setCellValue(userModel.songName)
                                cell = row1.createCell(4)
                                cell.setCellValue(userModel.songURL)
                            }

                            val folderName = "FileSongExcel"
                            val fileName = folderName + System.currentTimeMillis() + ".xls"
                            val path = Environment.getExternalStorageDirectory()
                                .toString() + File.separator + folderName + File.separator + fileName

                            val file = File(
                                    Environment.getExternalStorageDirectory()
                                        .toString() + File.separator + folderName
                                )
                            if (!file.exists()) {
                                file.mkdirs()
                            }

                            var outputStream: FileOutputStream? = null
                            try {
                                outputStream = FileOutputStream(path)
                                Log.d("RTY","path"+path)
                                Log.d("RTY","outputStream"+outputStream)
                                wb.write(outputStream)
                                Toast.makeText(
                                    FacebookSdk.getApplicationContext(),
                                    "Excel Created in $path",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            } catch (e: IOException) {
                                e.printStackTrace()
                                Toast.makeText(
                                    FacebookSdk.getApplicationContext(),
                                    "Failed to create Excel file",
                                    Toast.LENGTH_LONG
                                ).show()
                            } finally {
                                try {
                                    outputStream?.close()
                                } catch (e: IOException) {
                                    e.printStackTrace()
                                }
                            }
                        } else {
                            Toast.makeText(
                                FacebookSdk.getApplicationContext(),
                                "list are empty",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }

            }
        },
        onFailure = { exception ->

        })
        return binding.root
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Người dùng đã cấp quyền truy cập bộ nhớ ngoài, tiếp tục xử lý tạo file excel
        }
    }
}