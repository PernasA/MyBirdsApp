package com.pernasa.varillasbirdsapp.views

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pernasa.varillasbirdsapp.R
import com.pernasa.varillasbirdsapp.ui.theme.SkyBluePrimary
import com.pernasa.varillasbirdsapp.ui.theme.SkyBlueSecondary
import com.pernasa.varillasbirdsapp.ui.theme.VarillasBirdsAppTheme
import com.pernasa.varillasbirdsapp.ui.theme.GreenLime
import com.pernasa.varillasbirdsapp.ui.theme.SkyBlueSecondaryLight
import com.pernasa.varillasbirdsapp.utils.Constants.Companion.BIG_TEXT_SIZE
import com.pernasa.varillasbirdsapp.utils.Constants.Companion.MEDIUM_TEXT_SIZE
import com.pernasa.varillasbirdsapp.utils.Constants.Companion.SUBTITLE_TEXT_SIZE
import com.pernasa.varillasbirdsapp.utils.Constants.Companion.TITLE_TEXT_SIZE

@Composable
fun AboutUsPage() {
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 1.dp, start = 3.dp, end = 3.dp, bottom = 1.dp)
    ) {
        item {
            WriterCard()
        }
        item {
            DeveloperCard()
        }

    }
}

@Composable
fun WriterCard() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = SkyBlueSecondary,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 22.dp,
        ),
        border = BorderStroke(2.dp, SkyBluePrimary),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, top = 8.dp, end = 4.dp, bottom = 1.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RowNamePhotoHugo()
            Text(
                modifier = Modifier
                    .padding(top = 10.dp, start = 5.dp, end = 5.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.writer_full_description),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = MEDIUM_TEXT_SIZE,
                    lineHeight = MEDIUM_TEXT_SIZE,
                    textAlign = TextAlign.Justify,
                    color = Color.Black,
                    lineBreak = LineBreak.Paragraph
                )
            )
        }
    }
}

@Composable
private fun RowNamePhotoHugo() {
    Row(
        Modifier.fillMaxWidth().padding(top = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Image(
            modifier = Modifier
                .size(120.dp)
                .border(BorderStroke(1.3.dp, GreenLime), RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp)),
            painter = painterResource(R.drawable.util_hugo_foto),
            contentDescription = stringResource(R.string.image_book_description),
            contentScale = ContentScale.Fit
        )
        Text(
            modifier = Modifier
                .padding(top = 10.dp),
            text = stringResource(R.string.writer_name),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = BIG_TEXT_SIZE,
                lineHeight = BIG_TEXT_SIZE,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        )
        ExtendedContactWriterButton(Modifier.align(Alignment.CenterVertically))
    }
}


@Composable
fun ExtendedContactWriterButton(modifier: Modifier) {
    val context = LocalContext.current
    val whatsappIcon: ImageVector = ImageVector.vectorResource(id = R.drawable.icon_whatsapp_icon)

    LargeFloatingActionButton(
        onClick = { openWhatsappWriter(context) },
        containerColor = Color.Black,
        contentColor = GreenLime,
        modifier = modifier
            .size(50.dp)
    ) {
        Icon(
            whatsappIcon,
            stringResource(R.string.button_share),
            modifier.size(25.dp)
        )
    }
}

fun openWhatsappWriter(context: Context) {
    val phoneNumber = "+5493533685149"
    val message = "Buenos días, Hugo. Te escribo desde la aplicación de Aves de Las Varillas."
    val uri = Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber&text=${Uri.encode(message)}")
    val intent = Intent(Intent.ACTION_VIEW, uri)

    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context,"Whatsapp no está instalado", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun ExtendedContactDeveloperButton(modifier: Modifier) {
    val context = LocalContext.current
    val linkedInIcon: ImageVector = ImageVector.vectorResource(id = R.drawable.icon_linkedin_icon)

    ExtendedFloatingActionButton(
        onClick = { openLinkedIn(context) },
        text = { Text(stringResource(R.string.button_linkedIn), fontSize = MEDIUM_TEXT_SIZE) },
        icon = { Icon(
            linkedInIcon,
            stringResource(R.string.button_share),
            modifier.height(15.dp)
        ) },
        containerColor = Color.Black,
        contentColor = GreenLime,
        modifier = modifier
            .height(50.dp)
            .padding(top = 8.dp, bottom = 8.dp)
    )
}

fun openLinkedIn(context: Context) {
    val profileUrl = "https://www.linkedin.com/in/agustinbpernas"
    val uri = Uri.parse(profileUrl)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    context.startActivity(intent)
}

@Composable
fun DeveloperCard() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = SkyBlueSecondary,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 22.dp,
        ),
        border = BorderStroke(2.dp, SkyBluePrimary),
        modifier = Modifier
            .height(140.dp)
            .padding(start = 4.dp, top = 15.dp, end = 4.dp, bottom = 15.dp)
    ) {
        Row (
            Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                modifier = Modifier
                    .width(110.dp)
                    .padding(vertical = 10.dp, horizontal = 10.dp)
                    .border(BorderStroke(1.3.dp, GreenLime), RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp)),
                painter = painterResource(R.drawable.util_agustin_photo),
                contentDescription = stringResource(R.string.image_book_description),
                contentScale = ContentScale.Fit
            )
            Column (
                Modifier
                    .fillMaxSize()
                    .padding(end = 15.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                    text = stringResource(R.string.developer_name),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = TITLE_TEXT_SIZE,
                        lineHeight = TITLE_TEXT_SIZE,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                )
                ExtendedContactDeveloperButton(Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutUsPagePreview() {
    VarillasBirdsAppTheme () {
        AboutUsPage()
    }
}
