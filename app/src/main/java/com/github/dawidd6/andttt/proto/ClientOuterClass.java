// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: client.proto

package com.github.dawidd6.andttt.proto;

public final class ClientOuterClass {
  private ClientOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_proto_Client_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_proto_Client_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\014client.proto\022\005proto\032\013enums.proto\032\037goog" +
      "le/protobuf/timestamp.proto\"\213\001\n\006Client\022\014" +
      "\n\004name\030\001 \001(\t\022\014\n\004room\030\002 \001(\t\022)\n\005since\030\004 \001(" +
      "\0132\032.google.protobuf.Timestamp\022\035\n\006symbol\030" +
      "\005 \001(\0162\r.proto.Symbol\022\014\n\004turn\030\006 \001(\010\022\r\n\005re" +
      "ady\030\007 \001(\010B#\n\037com.github.dawidd6.andttt.p" +
      "rotoP\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.github.dawidd6.andttt.proto.Enums.getDescriptor(),
          com.google.protobuf.TimestampProto.getDescriptor(),
        }, assigner);
    internal_static_proto_Client_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_proto_Client_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_proto_Client_descriptor,
        new java.lang.String[] { "Name", "Room", "Since", "Symbol", "Turn", "Ready", });
    com.github.dawidd6.andttt.proto.Enums.getDescriptor();
    com.google.protobuf.TimestampProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
