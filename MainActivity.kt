package com.farid.aivideo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { FaridApp() }
    }
}

private val Violet = Color(0xFF7654E8)
private val Deep = Color(0xFF17132B)
private val Ink = Color(0xFF211B38)

@Composable
fun FaridApp() {
    var language by remember { mutableStateOf("پښتو") }
    var selected by remember { mutableStateOf("home") }
    var prompt by remember { mutableStateOf("") }
    var notice by remember { mutableStateOf("د AI ویډیو جوړولو خدمت به د API له نښلولو وروسته فعال شي.") }
    val isPashto = language == "پښتو"

    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Violet, background = Deep, surface = Ink,
            onBackground = Color.White, onSurface = Color.White
        )
    ) {
        Scaffold(
            containerColor = Deep,
            bottomBar = {
                NavigationBar(containerColor = Color(0xFF211B38)) {
                    NavigationBarItem(selected == "home", { selected = "home" },
                        icon = { Icon(Icons.Default.Home, null) }, label = { Text(if (isPashto) "کور" else "Home") })
                    NavigationBarItem(selected == "create", { selected = "create" },
                        icon = { Icon(Icons.Default.AutoAwesome, null) }, label = { Text(if (isPashto) "جوړول" else "Create") })
                    NavigationBarItem(selected == "library", { selected = "library" },
                        icon = { Icon(Icons.Default.VideoLibrary, null) }, label = { Text(if (isPashto) "زما ویډیوګانې" else "My videos") })
                }
            }
        ) { padding ->
            Column(
                Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState())
                    .padding(horizontal = 18.dp, vertical = 14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Text("Farid AI Video", fontSize = 25.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                        Text("Your Imagination, Our AI Magic", color = Color(0xFFBEB4E8), fontSize = 12.sp)
                    }
                    var expanded by remember { mutableStateOf(false) }
                    Box {
                        TextButton(onClick = { expanded = true }) {
                            Icon(Icons.Default.Language, null, tint = Color.White)
                            Spacer(Modifier.width(5.dp))
                            Text(language, color = Color.White)
                        }
                        DropdownMenu(expanded, { expanded = false }) {
                            listOf("پښتو", "دري", "العربية", "English").forEach {
                                DropdownMenuItem(text = { Text(it) }, onClick = { language = it; expanded = false })
                            }
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))
                Box(
                    Modifier.fillMaxWidth().background(
                        Brush.linearGradient(listOf(Color(0xFF6946D8), Color(0xFF2365B9))),
                        RoundedCornerShape(24.dp)
                    ).padding(20.dp)
                ) {
                    Column {
                        Text(if (isPashto) "خپل خیال په ویډیو بدل کړه" else "Turn your ideas into videos",
                            fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Spacer(Modifier.height(8.dp))
                        Text(if (isPashto) "متن، عکس یا خپل غږ وکاروه." else "Start with text, an image, or your own voice.",
                            color = Color.White.copy(alpha = .88f))
                        Spacer(Modifier.height(14.dp))
                        Button(onClick = { selected = "create" }, colors = ButtonDefaults.buttonColors(containerColor = Color.White)) {
                            Text(if (isPashto) "ویډیو جوړه کړه" else "Create a video", color = Violet)
                            Spacer(Modifier.width(6.dp))
                            Icon(Icons.Default.ArrowForward, null, tint = Violet)
                        }
                    }
                }

                Spacer(Modifier.height(22.dp))
                Text(if (isPashto) "د جوړولو لارې" else "Create with AI", fontSize = 19.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(12.dp))
                FeatureCard(Icons.Default.Edit, if (isPashto) "له متن څخه ویډیو" else "Text to Video",
                    if (isPashto) "خپل نظر ولیکه او ویډیو ته یې واړوه" else "Describe your idea and turn it into a video") { selected = "create" }
                FeatureCard(Icons.Default.Image, if (isPashto) "له عکس څخه ویډیو" else "Image to Video",
                    if (isPashto) "عکس انتخاب کړه او متحرک یې کړه" else "Choose a photo and animate it") { notice = if (isPashto) "د عکس اپلوډ به د میډیا فیچر په بل پړاو کې ورزیات شي." else "Image upload will be connected in the next development step." }
                FeatureCard(Icons.Default.Mic, if (isPashto) "زما غږ / Voice to Video" else "My Voice / Voice to Video",
                    if (isPashto) "خپل غږ ثبت یا پورته کړه" else "Record or upload your own voice") { notice = if (isPashto) "د غږ ثبتولو او اپلوډ فیچر په راتلونکي پړاو کې نښلول کېږي." else "Voice recording and upload will be connected in the next development step." }
                FeatureCard(Icons.Default.RecordVoiceOver, if (isPashto) "AI غږ او Lip Sync" else "AI Voice & Lip Sync",
                    if (isPashto) "غږ او د شونډو حرکت همغږي کړه" else "Sync speech with mouth movements") { notice = if (isPashto) "د Lip Sync لپاره به جلا AI خدمت ته اړتیا وي." else "Lip Sync requires an external AI service." }

                if (selected == "create") {
                    Spacer(Modifier.height(18.dp))
                    Text(if (isPashto) "د ویډیو تشریح" else "Describe your video", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = prompt, onValueChange = { prompt = it },
                        modifier = Modifier.fillMaxWidth(), minLines = 3,
                        placeholder = { Text(if (isPashto) "د ویډیو په اړه ولیکه..." else "Describe the video you want...") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White, unfocusedTextColor = Color.White,
                            focusedBorderColor = Violet, unfocusedBorderColor = Color(0xFF625A79),
                            focusedPlaceholderColor = Color.LightGray, unfocusedPlaceholderColor = Color.LightGray
                        )
                    )
                    Spacer(Modifier.height(10.dp))
                    Button(onClick = {
                        notice = if (prompt.isBlank()) {
                            if (isPashto) "لومړی د ویډیو تشریح ولیکه." else "Please enter a video description first."
                        } else {
                            if (isPashto) "ستاسې متن ثبت شو. د ویډیو تولید لپاره باید وړیا AI بیک‌اېنډ ورسره ونښلول شي." else "Prompt saved. Connect an AI video backend to generate the actual video."
                        }
                    }, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Default.AutoAwesome, null); Spacer(Modifier.width(8.dp))
                        Text(if (isPashto) "ویډیو تولید کړه" else "Generate video")
                    }
                }

                Spacer(Modifier.height(16.dp))
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF302747))) {
                    Row(Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
                        Icon(Icons.Default.Info, null, tint = Color(0xFFC9B9FF))
                        Spacer(Modifier.width(10.dp))
                        Text(notice, color = Color(0xFFE2DDF0), fontSize = 13.sp)
                    }
                }
                Spacer(Modifier.height(18.dp))
                Text(if (isPashto) "وړیا کارول زموږ هدف دی؛ د نامحدود AI تولید تضمین د خدمتونو او سرورونو په لګښت پورې تړلی دی." else "Free use is the goal; unlimited AI generation depends on available services and server costs.",
                    color = Color(0xFFAAA2BE), fontSize = 11.sp)
                Spacer(Modifier.height(18.dp))
            }
        }
    }
}

@Composable
private fun FeatureCard(icon: ImageVector, title: String, subtitle: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp).clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Ink),
        shape = RoundedCornerShape(18.dp)
    ) {
        Row(Modifier.padding(15.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(46.dp).background(Color(0xFF3B2E66), RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center) {
                Icon(icon, null, tint = Color(0xFFC7B6FF), modifier = Modifier.size(24.dp))
            }
            Spacer(Modifier.width(13.dp))
            Column(Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(Modifier.height(3.dp))
                Text(subtitle, color = Color(0xFFBDB5D0), fontSize = 12.sp)
            }
            Icon(Icons.Default.ChevronRight, null, tint = Color(0xFFBDB5D0))
        }
    }
}
