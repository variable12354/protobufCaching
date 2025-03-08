package com.example.protobuftask.localstore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.protobuftask.model.UserItemsProto
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object UserProtoSerializer: Serializer<UserItemsProto> {

    override val defaultValue: UserItemsProto
        get() = UserItemsProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserItemsProto {
        try {
            return UserItemsProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: UserItemsProto, output: OutputStream) = t.writeTo(output)

}
