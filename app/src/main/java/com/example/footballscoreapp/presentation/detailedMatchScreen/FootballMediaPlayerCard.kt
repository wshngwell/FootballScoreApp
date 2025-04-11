package com.example.footballscoreapp.presentation.detailedMatchScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.footballscoreapp.R
import com.example.footballscoreapp.presentation.MyProgressbar
import com.example.footballscoreapp.ui.theme.activeTrackPlayerColor
import com.example.footballscoreapp.ui.theme.inactiveTrackPlayerColor
import com.example.footballscoreapp.ui.theme.thumbPlayerColor
import com.example.footballscoreapp.ui.theme.timeOfPlayerColor
import com.example.footballscoreapp.ui.theme.videoBufferedProgressBarBrush
import com.example.footballscoreapp.ui.theme.videoControlButtonsTopPadding
import com.example.footballscoreapp.ui.theme.videoControlsButtonsColor
import com.example.footballscoreapp.ui.theme.videoCurrentTimeAndFullTimeStartPadding
import com.example.footballscoreapp.ui.theme.videoProgressBarBorderWith
import java.util.concurrent.TimeUnit

@Preview
@Composable
fun FootballMediaPlayerCard(
    modifier: Modifier = Modifier,
    updateShouldControlsButtonsBeVisible: () -> Unit = {},
    shouldControlsButtonsBeVisible: Boolean = false,
    isPlaying: Boolean = false,
    isBuffered: Boolean = false,
    playingStateChange: () -> Unit = {},
    currentPositionOfSlider: Long = 0L,
    changeIsSliderDragging: (Boolean) -> Unit = {},
    updateCurrentPositionOfSlider: (Long) -> Unit = {},
    videoDuration: Long = 0L,
    mediaPlayer: ExoPlayer = ExoPlayer.Builder(LocalContext.current).build(),
    updateIsVideoFullScreen: () -> Unit = {}
) {
    var playerView: PlayerView? by remember {
        mutableStateOf(null)
    }
    DisposableEffect(Unit) {
        onDispose {
            playerView?.player = null
        }
    }
    Box(
        Modifier
            .fillMaxSize()
            .clickable {
                updateShouldControlsButtonsBeVisible()
            },
    ) {
        AndroidView(
            factory = {
                val view = PlayerView(it).apply {
                    useController = false
                    player = mediaPlayer

                }
                playerView = view
                view
            },
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(16 / 9f)
                .align(Alignment.Center)
        )
        if (shouldControlsButtonsBeVisible) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(40.dp),
                onClick = {
                    playingStateChange()
                }
            ) {
                if (isBuffered) {
                    MyProgressbar(
                        modifier = Modifier.align(Alignment.Center),
                        brush = videoBufferedProgressBarBrush,
                        widthOfBorder = videoProgressBarBorderWith
                    )
                } else {
                    Image(
                        modifier = Modifier
                            .fillMaxSize(),
                        painter = when {
                            isPlaying -> painterResource(id = R.drawable.pause)
                            else -> painterResource(id = R.drawable.play)
                        },
                        contentDescription = stringResource(R.string.play_pause),
                        colorFilter = ColorFilter.tint(color = videoControlsButtonsColor)
                    )
                }
            }
            BottomControls(
                modifier = Modifier.align(Alignment.BottomCenter),
                currentPositionOfSlider = currentPositionOfSlider,
                changeIsSliderDragging = changeIsSliderDragging,
                updateCurrentPositionOfSlider = updateCurrentPositionOfSlider,
                videoDuration = videoDuration,
                updateIsVideoFullScreen = updateIsVideoFullScreen
            )
        }
    }
}

@Composable
private fun BottomControls(
    modifier: Modifier = Modifier,
    currentPositionOfSlider: Long = 0L,
    changeIsSliderDragging: (Boolean) -> Unit = {},
    updateCurrentPositionOfSlider: (Long) -> Unit,
    videoDuration: Long = 0L,
    updateIsVideoFullScreen: () -> Unit = {}
) {
    Column(modifier = modifier) {
        Slider(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
            value = currentPositionOfSlider.toFloat(),
            onValueChange = {
                changeIsSliderDragging(true)
                updateCurrentPositionOfSlider(it.toLong())
            },
            onValueChangeFinished = {
                changeIsSliderDragging(false)
            },
            valueRange = 0f..videoDuration.toFloat(),
            colors =
            SliderDefaults.colors(
                thumbColor = thumbPlayerColor,
                inactiveTrackColor = inactiveTrackPlayerColor,
                activeTrackColor = activeTrackPlayerColor
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = videoControlButtonsTopPadding)
        ) {
            Text(
                modifier = Modifier.padding(start = videoCurrentTimeAndFullTimeStartPadding),
                text = (currentPositionOfSlider.formatMinSec() + stringResource(R.string.slash) +
                        videoDuration.formatMinSec()),
                color = timeOfPlayerColor
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { updateIsVideoFullScreen() }) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.full_screen),
                    contentDescription = stringResource(R.string.full_screen_button),
                    colorFilter = ColorFilter.tint(color = videoControlsButtonsColor)
                )
            }
        }
    }
}

@SuppressLint("DefaultLocale")
private fun Long.formatMinSec(): String {
    return String.format(
        "%02d:%02d",
        TimeUnit.MILLISECONDS.toMinutes(this),
        TimeUnit.MILLISECONDS.toSeconds(this) -
                TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(this)
                )
    )
}