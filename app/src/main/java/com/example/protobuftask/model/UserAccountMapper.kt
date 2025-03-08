package com.example.protobuftask.model

fun UsersProto.toDomain() = UserResponseItem(
    id = id.toInt(),
    name = name,
    username = username,
    email = email,
    address = address.toDomain(),
    phone = phone,
    website = website,
    company = company.toDomain()
)

fun AddressProto.toDomain() = Address(
    city = city,
    geo = geo.toDomain(),
    street = street,
    suite = suite,
    zipcode = zipcode
)

fun CompanyProto.toDomain() = Company(
    bs = bs,
    catchPhrase = catchPhrase,
    name = name
)

fun GeoProto.toDomain() = Geo(
    lat = lat,
    lng = lng
)

