package uz.devmi.mvvmkattabozor.base.extention

import androidx.appcompat.widget.AppCompatImageView
import coil.load
import coil.transform.RoundedCornersTransformation
import uz.devmi.mvvmkattabozor.offer.data.model.Image

fun AppCompatImageView.loadImage(image: Image){
    load(image.url) {
        if (image.width > image.height) {
            transformations(RoundedCornersTransformation(8f))
        }
    }
}