package com.example.protobuftask.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.protobuftask.model.Address
import com.example.protobuftask.model.Company
import com.example.protobuftask.model.UserResponseItem
import com.example.protobuftask.ui.theme.ProtoBufTaskTheme
import com.example.protobuftask.viewmodel.UsersProfileViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: UsersProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProtoBufTaskTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserProfileScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun UserProfileScreen(viewModel: UsersProfileViewModel) {
    val users by viewModel.user.collectAsState()

    if(users.isEmpty()){
        LoadingView()
    }else{
        LazyColumn {
            items(users){user->
                ProfileCard(user)
            }
        }
    }
}

@Composable
fun ProfileCard(user: UserResponseItem) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = user.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E88E5)
            )
            Text(
                text = "@${user.username}",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))

            SelectionContainer {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = user.email, fontSize = 14.sp)
                    Text(text = user.phone, fontSize = 14.sp)
                    Text(text = user.website, fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Divider(color = Color.LightGray, thickness = 1.dp)

            AddressSection(user.address)
            CompanySection(user.company)
        }
    }
}

@Composable
fun AddressSection(address: Address) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Text(text = "📍 Address", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text(text = "${address.street}, ${address.suite}", fontSize = 14.sp, color = Color.DarkGray)
        Text(text = "${address.city} - ${address.zipcode}", fontSize = 14.sp, color = Color.DarkGray)
    }
}

@Composable
fun CompanySection(company: Company) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Text(text = "🏢 Company", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text(text = company.name, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        Text(text = "\"${company.catchPhrase}\"", fontSize = 14.sp, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic, color = Color.Gray)
    }
}


@Composable
fun LoadingView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color.Blue)
    }
}