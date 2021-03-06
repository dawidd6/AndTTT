// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: api.proto

package com.github.dawidd6.andttt.proto;

/**
 * Protobuf type {@code proto.ApiRoom}
 */
public  final class ApiRoom extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:proto.ApiRoom)
    ApiRoomOrBuilder {
  // Use ApiRoom.newBuilder() to construct.
  private ApiRoom(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ApiRoom() {
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private ApiRoom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!input.skipField(tag)) {
              done = true;
            }
            break;
          }
          case 10: {
            com.github.dawidd6.andttt.proto.Room.Builder subBuilder = null;
            if (room_ != null) {
              subBuilder = room_.toBuilder();
            }
            room_ = input.readMessage(com.github.dawidd6.andttt.proto.Room.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(room_);
              room_ = subBuilder.buildPartial();
            }

            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.github.dawidd6.andttt.proto.Api.internal_static_proto_ApiRoom_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.github.dawidd6.andttt.proto.Api.internal_static_proto_ApiRoom_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.github.dawidd6.andttt.proto.ApiRoom.class, com.github.dawidd6.andttt.proto.ApiRoom.Builder.class);
  }

  public static final int ROOM_FIELD_NUMBER = 1;
  private com.github.dawidd6.andttt.proto.Room room_;
  /**
   * <code>.proto.Room room = 1;</code>
   */
  public boolean hasRoom() {
    return room_ != null;
  }
  /**
   * <code>.proto.Room room = 1;</code>
   */
  public com.github.dawidd6.andttt.proto.Room getRoom() {
    return room_ == null ? com.github.dawidd6.andttt.proto.Room.getDefaultInstance() : room_;
  }
  /**
   * <code>.proto.Room room = 1;</code>
   */
  public com.github.dawidd6.andttt.proto.RoomOrBuilder getRoomOrBuilder() {
    return getRoom();
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (room_ != null) {
      output.writeMessage(1, getRoom());
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (room_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getRoom());
    }
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.github.dawidd6.andttt.proto.ApiRoom)) {
      return super.equals(obj);
    }
    com.github.dawidd6.andttt.proto.ApiRoom other = (com.github.dawidd6.andttt.proto.ApiRoom) obj;

    boolean result = true;
    result = result && (hasRoom() == other.hasRoom());
    if (hasRoom()) {
      result = result && getRoom()
          .equals(other.getRoom());
    }
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (hasRoom()) {
      hash = (37 * hash) + ROOM_FIELD_NUMBER;
      hash = (53 * hash) + getRoom().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.github.dawidd6.andttt.proto.ApiRoom parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.github.dawidd6.andttt.proto.ApiRoom parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.github.dawidd6.andttt.proto.ApiRoom parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.github.dawidd6.andttt.proto.ApiRoom parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.github.dawidd6.andttt.proto.ApiRoom parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.github.dawidd6.andttt.proto.ApiRoom parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.github.dawidd6.andttt.proto.ApiRoom parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.github.dawidd6.andttt.proto.ApiRoom parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.github.dawidd6.andttt.proto.ApiRoom parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.github.dawidd6.andttt.proto.ApiRoom parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.github.dawidd6.andttt.proto.ApiRoom parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.github.dawidd6.andttt.proto.ApiRoom parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.github.dawidd6.andttt.proto.ApiRoom prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code proto.ApiRoom}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:proto.ApiRoom)
      com.github.dawidd6.andttt.proto.ApiRoomOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.github.dawidd6.andttt.proto.Api.internal_static_proto_ApiRoom_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.github.dawidd6.andttt.proto.Api.internal_static_proto_ApiRoom_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.github.dawidd6.andttt.proto.ApiRoom.class, com.github.dawidd6.andttt.proto.ApiRoom.Builder.class);
    }

    // Construct using com.github.dawidd6.andttt.proto.ApiRoom.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      if (roomBuilder_ == null) {
        room_ = null;
      } else {
        room_ = null;
        roomBuilder_ = null;
      }
      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.github.dawidd6.andttt.proto.Api.internal_static_proto_ApiRoom_descriptor;
    }

    public com.github.dawidd6.andttt.proto.ApiRoom getDefaultInstanceForType() {
      return com.github.dawidd6.andttt.proto.ApiRoom.getDefaultInstance();
    }

    public com.github.dawidd6.andttt.proto.ApiRoom build() {
      com.github.dawidd6.andttt.proto.ApiRoom result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public com.github.dawidd6.andttt.proto.ApiRoom buildPartial() {
      com.github.dawidd6.andttt.proto.ApiRoom result = new com.github.dawidd6.andttt.proto.ApiRoom(this);
      if (roomBuilder_ == null) {
        result.room_ = room_;
      } else {
        result.room_ = roomBuilder_.build();
      }
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.github.dawidd6.andttt.proto.ApiRoom) {
        return mergeFrom((com.github.dawidd6.andttt.proto.ApiRoom)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.github.dawidd6.andttt.proto.ApiRoom other) {
      if (other == com.github.dawidd6.andttt.proto.ApiRoom.getDefaultInstance()) return this;
      if (other.hasRoom()) {
        mergeRoom(other.getRoom());
      }
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.github.dawidd6.andttt.proto.ApiRoom parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.github.dawidd6.andttt.proto.ApiRoom) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private com.github.dawidd6.andttt.proto.Room room_ = null;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.github.dawidd6.andttt.proto.Room, com.github.dawidd6.andttt.proto.Room.Builder, com.github.dawidd6.andttt.proto.RoomOrBuilder> roomBuilder_;
    /**
     * <code>.proto.Room room = 1;</code>
     */
    public boolean hasRoom() {
      return roomBuilder_ != null || room_ != null;
    }
    /**
     * <code>.proto.Room room = 1;</code>
     */
    public com.github.dawidd6.andttt.proto.Room getRoom() {
      if (roomBuilder_ == null) {
        return room_ == null ? com.github.dawidd6.andttt.proto.Room.getDefaultInstance() : room_;
      } else {
        return roomBuilder_.getMessage();
      }
    }
    /**
     * <code>.proto.Room room = 1;</code>
     */
    public Builder setRoom(com.github.dawidd6.andttt.proto.Room value) {
      if (roomBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        room_ = value;
        onChanged();
      } else {
        roomBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.proto.Room room = 1;</code>
     */
    public Builder setRoom(
        com.github.dawidd6.andttt.proto.Room.Builder builderForValue) {
      if (roomBuilder_ == null) {
        room_ = builderForValue.build();
        onChanged();
      } else {
        roomBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.proto.Room room = 1;</code>
     */
    public Builder mergeRoom(com.github.dawidd6.andttt.proto.Room value) {
      if (roomBuilder_ == null) {
        if (room_ != null) {
          room_ =
            com.github.dawidd6.andttt.proto.Room.newBuilder(room_).mergeFrom(value).buildPartial();
        } else {
          room_ = value;
        }
        onChanged();
      } else {
        roomBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.proto.Room room = 1;</code>
     */
    public Builder clearRoom() {
      if (roomBuilder_ == null) {
        room_ = null;
        onChanged();
      } else {
        room_ = null;
        roomBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.proto.Room room = 1;</code>
     */
    public com.github.dawidd6.andttt.proto.Room.Builder getRoomBuilder() {
      
      onChanged();
      return getRoomFieldBuilder().getBuilder();
    }
    /**
     * <code>.proto.Room room = 1;</code>
     */
    public com.github.dawidd6.andttt.proto.RoomOrBuilder getRoomOrBuilder() {
      if (roomBuilder_ != null) {
        return roomBuilder_.getMessageOrBuilder();
      } else {
        return room_ == null ?
            com.github.dawidd6.andttt.proto.Room.getDefaultInstance() : room_;
      }
    }
    /**
     * <code>.proto.Room room = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.github.dawidd6.andttt.proto.Room, com.github.dawidd6.andttt.proto.Room.Builder, com.github.dawidd6.andttt.proto.RoomOrBuilder> 
        getRoomFieldBuilder() {
      if (roomBuilder_ == null) {
        roomBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.github.dawidd6.andttt.proto.Room, com.github.dawidd6.andttt.proto.Room.Builder, com.github.dawidd6.andttt.proto.RoomOrBuilder>(
                getRoom(),
                getParentForChildren(),
                isClean());
        room_ = null;
      }
      return roomBuilder_;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:proto.ApiRoom)
  }

  // @@protoc_insertion_point(class_scope:proto.ApiRoom)
  private static final com.github.dawidd6.andttt.proto.ApiRoom DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.github.dawidd6.andttt.proto.ApiRoom();
  }

  public static com.github.dawidd6.andttt.proto.ApiRoom getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ApiRoom>
      PARSER = new com.google.protobuf.AbstractParser<ApiRoom>() {
    public ApiRoom parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new ApiRoom(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ApiRoom> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ApiRoom> getParserForType() {
    return PARSER;
  }

  public com.github.dawidd6.andttt.proto.ApiRoom getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

