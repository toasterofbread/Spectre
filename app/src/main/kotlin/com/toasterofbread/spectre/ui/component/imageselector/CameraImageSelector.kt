package com.toasterofbread.spectre.ui.component.imageselector

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.FlipCameraAndroid
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.toasterofbread.spectre.model.ImageProvider
import com.toasterofbread.toastercomposetools.settings.ui.Theme
import com.toasterofbread.toastercomposetools.utils.composable.ShapedIconButton

class CameraImageSelector: ImageSelector {
    override fun getIcon(): ImageVector =
        Icons.Default.CameraAlt

    @Composable
    override fun Selector(
        theme: Theme,
        image_provider: ImageProvider,
        content_alignment: Alignment,
        content_padding: PaddingValues,
        content_shape: Shape,
        modifier: Modifier,
        onSelectedImageChanged: (ImageBitmap?) -> Unit
    ) {
        Box(modifier.padding(content_padding), contentAlignment = content_alignment) {
            var front_lens: Boolean by remember { mutableStateOf(false) }

            image_provider.CameraPreview(
                Modifier.fillMaxSize(),
                front_lens = front_lens,
                content_modifier = Modifier.fillMaxSize().clip(content_shape),
                content_alignment = Alignment.BottomCenter
            )

            ShapedIconButton(
                { front_lens = !front_lens },
                IconButtonDefaults.iconButtonColors(
                    containerColor = theme.accent,
                    contentColor = theme.on_accent
                ),
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(10.dp)
            ) {
                val icon_rotation: Float by animateFloatAsState(
                    if (front_lens) 180f else 0f,
                    spring(Spring.DampingRatioLowBouncy, Spring.StiffnessVeryLow)
                )
                Icon(Icons.Default.FlipCameraAndroid, null, Modifier.graphicsLayer { rotationZ = icon_rotation })
            }
        }
    }
}