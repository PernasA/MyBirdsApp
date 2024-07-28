package com.example.mybirdsapp.views

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.sp
import com.example.mybirdsapp.R
import com.example.mybirdsapp.ui.theme.MossGreenPrimary
import com.example.mybirdsapp.ui.theme.MossGreenSecondary
import com.example.mybirdsapp.ui.theme.MyBirdsAppTheme
import com.example.mybirdsapp.ui.theme.OrangeBird
import com.example.mybirdsapp.utils.Constants.Companion.MEDIUM_TEXT_SIZE
import com.example.mybirdsapp.utils.Constants.Companion.SUBTITLE_TEXT_SIZE
import com.example.mybirdsapp.utils.Constants.Companion.TITLE_TEXT_SIZE

@Composable
fun AboutUsPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 5.dp, start = 9.dp, end = 9.dp, bottom = 5.dp)
    ) {
        Column (Modifier.weight(1F)) {
            BookCard(Modifier.weight(1F))
            WriterCard(Modifier.weight(1F))
        }
        DeveloperCard()
    }
}

@Composable
fun BookCard(modifier: Modifier) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MossGreenSecondary,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 22.dp,
        ),
        border = BorderStroke(2.dp, MossGreenPrimary),
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 4.dp, top = 15.dp, end = 4.dp, bottom = 6.dp)
    ) {
        Row (
            Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                modifier = Modifier
                    .width(150.dp)
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .border(BorderStroke(0.8.dp, OrangeBird), RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp)),
                painter = painterResource(R.drawable.util_book_front),
                contentDescription = stringResource(R.string.image_book_description),
                contentScale = ContentScale.Fit
            )
            Column (
                Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterVertically)
                    .padding(start = 2.dp, end = 18.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                    text = stringResource(R.string.book_name),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = TITLE_TEXT_SIZE,
                        lineHeight = TITLE_TEXT_SIZE,
                        letterSpacing = 0.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                )
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                    text = stringResource(R.string.book_full_description),
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = SUBTITLE_TEXT_SIZE,
                        lineHeight = SUBTITLE_TEXT_SIZE,
                        letterSpacing = 0.sp,
                        textAlign = TextAlign.Justify,
                        color = Color.Black,
                        lineBreak = LineBreak.Paragraph
                    )
                )
            }
        }
    }
}

@Composable
fun WriterCard(modifier: Modifier) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MossGreenSecondary,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 22.dp,
        ),
        border = BorderStroke(2.dp, MossGreenPrimary),
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 4.dp, top = 15.dp, end = 4.dp, bottom = 6.dp)
    ) {
        Row (
            Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                modifier = Modifier
                    .width(130.dp)
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .border(BorderStroke(0.8.dp, OrangeBird), RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp)),
                painter = painterResource(R.drawable.util_raul_balla),
                contentDescription = stringResource(R.string.image_book_description),
                contentScale = ContentScale.Fit
            )
            Column (
                Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterVertically)
                    .padding(start = 2.dp, end = 18.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                    text = stringResource(R.string.writer_name),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = TITLE_TEXT_SIZE,
                        lineHeight = TITLE_TEXT_SIZE,
                        letterSpacing = 0.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                )
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                    text = stringResource(R.string.writer_full_description),
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = SUBTITLE_TEXT_SIZE,
                        lineHeight = SUBTITLE_TEXT_SIZE,
                        letterSpacing = 0.sp,
                        textAlign = TextAlign.Justify,
                        color = Color.Black,
                        lineBreak = LineBreak.Paragraph
                    )
                )
                ExtendedContactWriterButton(Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}

@Composable
fun DeveloperCard() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MossGreenSecondary,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 22.dp,
        ),
        border = BorderStroke(2.dp, MossGreenPrimary),
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
                    .border(BorderStroke(0.8.dp, OrangeBird), RoundedCornerShape(8.dp))
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
                        letterSpacing = 0.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                )
                ExtendedContactDeveloperButton(Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}

@Composable
fun ExtendedContactWriterButton(modifier: Modifier) {
    val context = LocalContext.current
    val whatsappIcon: ImageVector = ImageVector.vectorResource(id = R.drawable.icon_whatsapp_icon)

    ExtendedFloatingActionButton(
        onClick = { openWhatsappWriter(context) },
        text = { Text(stringResource(R.string.button_whatsapp), fontSize = MEDIUM_TEXT_SIZE) },
        icon = { Icon(
                whatsappIcon,
                stringResource(R.string.button_share),
                modifier.height(20.dp)
            ) },
        containerColor = Color.Black,
        contentColor = OrangeBird,
        modifier = modifier
            .height(50.dp)
            .padding(top = 10.dp)
    )
}

fun openWhatsappWriter(context: Context) {
    val phoneNumber = "+5492984501199"
    val message = "Buenos días, Raul. Te escribo desde la aplicación de Aves de Merlo."
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
        contentColor = OrangeBird,
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

@Preview(showBackground = true)
@Composable
fun AboutUsPagePreview() {
    MyBirdsAppTheme (true) {
        AboutUsPage()
    }
}
