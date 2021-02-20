package com.example.mypokemonapp.screens.upload

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.mypokemonapp.R
import com.example.mypokemonapp.data.UploadResponse
import com.example.mypokemonapp.databinding.FragmentImageUploadBinding
import com.example.mypokemonapp.network.MyAPI
import com.example.mypokemonapp.utils.getFileName
import com.example.mypokemonapp.utils.snackbar
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class ImageUploadFragment : Fragment() {

    lateinit var binding: FragmentImageUploadBinding

    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 101
    }

    private var selectedImageUri: Uri? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageUploadBinding.inflate(layoutInflater)
        binding.imageView.setOnClickListener {
            openImageChooser()
        }

        binding.buttonUpload.setOnClickListener {
            uploadImage()
        }

        // Inflate the layout for this fragment
        return binding.root

    }


    /**
     * Involked when the upload button is click
     * It displays a toast if no image was selected and,
     * Runs the file upload if an image is selected
     */
    private fun uploadImage() {
        if (selectedImageUri == null) {
            Toast.makeText(requireContext(), "Please select an Image", Toast.LENGTH_SHORT).show()
            return
        } else {
            val parcelFileDescriptor = selectedImageUri!!.let {
                requireActivity().contentResolver.openFileDescriptor(
                    it, "r", null
                )
            } ?: return

            /**
             * Creates a FileInputStream by using the file descriptor fdObj,
             * which represents an existing
             * connection to an actual file in the file system.
             */
            val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)

            /**
             * Creates a new File instance from a parent abstract pathname and a child pathname string.
             */
            val file = File(
                requireContext().cacheDir,
                requireActivity().contentResolver.getFileName(selectedImageUri!!)
            )
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)

            //
            val body = UploadRequestBody(file, "image", this)

            /**
             * Calling the retrofit API request
             */
            MyAPI().uploadImage(
                MultipartBody.Part.createFormData(
                    "file",
                    file.name,
                    body
                )
            ).enqueue(object : Callback<UploadResponse> {
                override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                    binding.layoutRoot.snackbar(t.message!!)
                    Log.d("Status", "Failed")
                    binding.progressBar.progress = 0
                }

                /**
                 * Invoked for a received HTTP response.
                 * An HTTP response may still indicate an application-level failure such as a 404 or 500
                 */
                override fun onResponse(
                    call: Call<UploadResponse>,
                    response: Response<UploadResponse>
                ) {
                    response.body()?.let {
                        binding.layoutRoot.snackbar(it.message)
                        Log.d("server response: ", "${response.body()?.toString()}")
                        Log.d("Status", "Passed")
                        binding.progressBar.progress = 100
                    }
                }
            })
        }
    }

    /**
     * Request the user to choose an image to upload
     */

    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, REQUEST_CODE_PICK_IMAGE)
        }
    }

    /**
     * Receive the result from a previous call to startActivityForResult(Intent, int).
     * This follows the related Activity API as described there in
     * Activity.onActivityResult(int, int, Intent)
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_PICK_IMAGE -> {
                    selectedImageUri = data?.data
                    binding.imageView.setImageURI(selectedImageUri)
                }
            }
        }
    }
}