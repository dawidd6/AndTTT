// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: api.proto

package com.github.dawidd6.andttt.proto;

/**
 * Protobuf type {@code proto.ApiClients}
 */
public  final class ApiClients extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:proto.ApiClients)
    ApiClientsOrBuilder {
  // Use ApiClients.newBuilder() to construct.
  private ApiClients(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ApiClients() {
    count_ = 0;
    clients_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private ApiClients(
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
          case 8: {

            count_ = input.readInt32();
            break;
          }
          case 18: {
            if (!((mutable_bitField0_ & 0x00000002) == 0x00000002)) {
              clients_ = new java.util.ArrayList<com.github.dawidd6.andttt.proto.Client>();
              mutable_bitField0_ |= 0x00000002;
            }
            clients_.add(
                input.readMessage(com.github.dawidd6.andttt.proto.Client.parser(), extensionRegistry));
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
      if (((mutable_bitField0_ & 0x00000002) == 0x00000002)) {
        clients_ = java.util.Collections.unmodifiableList(clients_);
      }
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.github.dawidd6.andttt.proto.Api.internal_static_proto_ApiClients_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.github.dawidd6.andttt.proto.Api.internal_static_proto_ApiClients_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.github.dawidd6.andttt.proto.ApiClients.class, com.github.dawidd6.andttt.proto.ApiClients.Builder.class);
  }

  private int bitField0_;
  public static final int COUNT_FIELD_NUMBER = 1;
  private int count_;
  /**
   * <code>int32 count = 1;</code>
   */
  public int getCount() {
    return count_;
  }

  public static final int CLIENTS_FIELD_NUMBER = 2;
  private java.util.List<com.github.dawidd6.andttt.proto.Client> clients_;
  /**
   * <code>repeated .proto.Client clients = 2;</code>
   */
  public java.util.List<com.github.dawidd6.andttt.proto.Client> getClientsList() {
    return clients_;
  }
  /**
   * <code>repeated .proto.Client clients = 2;</code>
   */
  public java.util.List<? extends com.github.dawidd6.andttt.proto.ClientOrBuilder> 
      getClientsOrBuilderList() {
    return clients_;
  }
  /**
   * <code>repeated .proto.Client clients = 2;</code>
   */
  public int getClientsCount() {
    return clients_.size();
  }
  /**
   * <code>repeated .proto.Client clients = 2;</code>
   */
  public com.github.dawidd6.andttt.proto.Client getClients(int index) {
    return clients_.get(index);
  }
  /**
   * <code>repeated .proto.Client clients = 2;</code>
   */
  public com.github.dawidd6.andttt.proto.ClientOrBuilder getClientsOrBuilder(
      int index) {
    return clients_.get(index);
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
    if (count_ != 0) {
      output.writeInt32(1, count_);
    }
    for (int i = 0; i < clients_.size(); i++) {
      output.writeMessage(2, clients_.get(i));
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (count_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, count_);
    }
    for (int i = 0; i < clients_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, clients_.get(i));
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
    if (!(obj instanceof com.github.dawidd6.andttt.proto.ApiClients)) {
      return super.equals(obj);
    }
    com.github.dawidd6.andttt.proto.ApiClients other = (com.github.dawidd6.andttt.proto.ApiClients) obj;

    boolean result = true;
    result = result && (getCount()
        == other.getCount());
    result = result && getClientsList()
        .equals(other.getClientsList());
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + COUNT_FIELD_NUMBER;
    hash = (53 * hash) + getCount();
    if (getClientsCount() > 0) {
      hash = (37 * hash) + CLIENTS_FIELD_NUMBER;
      hash = (53 * hash) + getClientsList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.github.dawidd6.andttt.proto.ApiClients parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.github.dawidd6.andttt.proto.ApiClients parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.github.dawidd6.andttt.proto.ApiClients parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.github.dawidd6.andttt.proto.ApiClients parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.github.dawidd6.andttt.proto.ApiClients parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.github.dawidd6.andttt.proto.ApiClients parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.github.dawidd6.andttt.proto.ApiClients parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.github.dawidd6.andttt.proto.ApiClients parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.github.dawidd6.andttt.proto.ApiClients parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.github.dawidd6.andttt.proto.ApiClients parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.github.dawidd6.andttt.proto.ApiClients parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.github.dawidd6.andttt.proto.ApiClients parseFrom(
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
  public static Builder newBuilder(com.github.dawidd6.andttt.proto.ApiClients prototype) {
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
   * Protobuf type {@code proto.ApiClients}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:proto.ApiClients)
      com.github.dawidd6.andttt.proto.ApiClientsOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.github.dawidd6.andttt.proto.Api.internal_static_proto_ApiClients_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.github.dawidd6.andttt.proto.Api.internal_static_proto_ApiClients_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.github.dawidd6.andttt.proto.ApiClients.class, com.github.dawidd6.andttt.proto.ApiClients.Builder.class);
    }

    // Construct using com.github.dawidd6.andttt.proto.ApiClients.newBuilder()
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
        getClientsFieldBuilder();
      }
    }
    public Builder clear() {
      super.clear();
      count_ = 0;

      if (clientsBuilder_ == null) {
        clients_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000002);
      } else {
        clientsBuilder_.clear();
      }
      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.github.dawidd6.andttt.proto.Api.internal_static_proto_ApiClients_descriptor;
    }

    public com.github.dawidd6.andttt.proto.ApiClients getDefaultInstanceForType() {
      return com.github.dawidd6.andttt.proto.ApiClients.getDefaultInstance();
    }

    public com.github.dawidd6.andttt.proto.ApiClients build() {
      com.github.dawidd6.andttt.proto.ApiClients result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public com.github.dawidd6.andttt.proto.ApiClients buildPartial() {
      com.github.dawidd6.andttt.proto.ApiClients result = new com.github.dawidd6.andttt.proto.ApiClients(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      result.count_ = count_;
      if (clientsBuilder_ == null) {
        if (((bitField0_ & 0x00000002) == 0x00000002)) {
          clients_ = java.util.Collections.unmodifiableList(clients_);
          bitField0_ = (bitField0_ & ~0x00000002);
        }
        result.clients_ = clients_;
      } else {
        result.clients_ = clientsBuilder_.build();
      }
      result.bitField0_ = to_bitField0_;
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
      if (other instanceof com.github.dawidd6.andttt.proto.ApiClients) {
        return mergeFrom((com.github.dawidd6.andttt.proto.ApiClients)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.github.dawidd6.andttt.proto.ApiClients other) {
      if (other == com.github.dawidd6.andttt.proto.ApiClients.getDefaultInstance()) return this;
      if (other.getCount() != 0) {
        setCount(other.getCount());
      }
      if (clientsBuilder_ == null) {
        if (!other.clients_.isEmpty()) {
          if (clients_.isEmpty()) {
            clients_ = other.clients_;
            bitField0_ = (bitField0_ & ~0x00000002);
          } else {
            ensureClientsIsMutable();
            clients_.addAll(other.clients_);
          }
          onChanged();
        }
      } else {
        if (!other.clients_.isEmpty()) {
          if (clientsBuilder_.isEmpty()) {
            clientsBuilder_.dispose();
            clientsBuilder_ = null;
            clients_ = other.clients_;
            bitField0_ = (bitField0_ & ~0x00000002);
            clientsBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getClientsFieldBuilder() : null;
          } else {
            clientsBuilder_.addAllMessages(other.clients_);
          }
        }
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
      com.github.dawidd6.andttt.proto.ApiClients parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.github.dawidd6.andttt.proto.ApiClients) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private int count_ ;
    /**
     * <code>int32 count = 1;</code>
     */
    public int getCount() {
      return count_;
    }
    /**
     * <code>int32 count = 1;</code>
     */
    public Builder setCount(int value) {
      
      count_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 count = 1;</code>
     */
    public Builder clearCount() {
      
      count_ = 0;
      onChanged();
      return this;
    }

    private java.util.List<com.github.dawidd6.andttt.proto.Client> clients_ =
      java.util.Collections.emptyList();
    private void ensureClientsIsMutable() {
      if (!((bitField0_ & 0x00000002) == 0x00000002)) {
        clients_ = new java.util.ArrayList<com.github.dawidd6.andttt.proto.Client>(clients_);
        bitField0_ |= 0x00000002;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        com.github.dawidd6.andttt.proto.Client, com.github.dawidd6.andttt.proto.Client.Builder, com.github.dawidd6.andttt.proto.ClientOrBuilder> clientsBuilder_;

    /**
     * <code>repeated .proto.Client clients = 2;</code>
     */
    public java.util.List<com.github.dawidd6.andttt.proto.Client> getClientsList() {
      if (clientsBuilder_ == null) {
        return java.util.Collections.unmodifiableList(clients_);
      } else {
        return clientsBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .proto.Client clients = 2;</code>
     */
    public int getClientsCount() {
      if (clientsBuilder_ == null) {
        return clients_.size();
      } else {
        return clientsBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .proto.Client clients = 2;</code>
     */
    public com.github.dawidd6.andttt.proto.Client getClients(int index) {
      if (clientsBuilder_ == null) {
        return clients_.get(index);
      } else {
        return clientsBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .proto.Client clients = 2;</code>
     */
    public Builder setClients(
        int index, com.github.dawidd6.andttt.proto.Client value) {
      if (clientsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureClientsIsMutable();
        clients_.set(index, value);
        onChanged();
      } else {
        clientsBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .proto.Client clients = 2;</code>
     */
    public Builder setClients(
        int index, com.github.dawidd6.andttt.proto.Client.Builder builderForValue) {
      if (clientsBuilder_ == null) {
        ensureClientsIsMutable();
        clients_.set(index, builderForValue.build());
        onChanged();
      } else {
        clientsBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .proto.Client clients = 2;</code>
     */
    public Builder addClients(com.github.dawidd6.andttt.proto.Client value) {
      if (clientsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureClientsIsMutable();
        clients_.add(value);
        onChanged();
      } else {
        clientsBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .proto.Client clients = 2;</code>
     */
    public Builder addClients(
        int index, com.github.dawidd6.andttt.proto.Client value) {
      if (clientsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureClientsIsMutable();
        clients_.add(index, value);
        onChanged();
      } else {
        clientsBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .proto.Client clients = 2;</code>
     */
    public Builder addClients(
        com.github.dawidd6.andttt.proto.Client.Builder builderForValue) {
      if (clientsBuilder_ == null) {
        ensureClientsIsMutable();
        clients_.add(builderForValue.build());
        onChanged();
      } else {
        clientsBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .proto.Client clients = 2;</code>
     */
    public Builder addClients(
        int index, com.github.dawidd6.andttt.proto.Client.Builder builderForValue) {
      if (clientsBuilder_ == null) {
        ensureClientsIsMutable();
        clients_.add(index, builderForValue.build());
        onChanged();
      } else {
        clientsBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .proto.Client clients = 2;</code>
     */
    public Builder addAllClients(
        java.lang.Iterable<? extends com.github.dawidd6.andttt.proto.Client> values) {
      if (clientsBuilder_ == null) {
        ensureClientsIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, clients_);
        onChanged();
      } else {
        clientsBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .proto.Client clients = 2;</code>
     */
    public Builder clearClients() {
      if (clientsBuilder_ == null) {
        clients_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000002);
        onChanged();
      } else {
        clientsBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .proto.Client clients = 2;</code>
     */
    public Builder removeClients(int index) {
      if (clientsBuilder_ == null) {
        ensureClientsIsMutable();
        clients_.remove(index);
        onChanged();
      } else {
        clientsBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .proto.Client clients = 2;</code>
     */
    public com.github.dawidd6.andttt.proto.Client.Builder getClientsBuilder(
        int index) {
      return getClientsFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .proto.Client clients = 2;</code>
     */
    public com.github.dawidd6.andttt.proto.ClientOrBuilder getClientsOrBuilder(
        int index) {
      if (clientsBuilder_ == null) {
        return clients_.get(index);  } else {
        return clientsBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .proto.Client clients = 2;</code>
     */
    public java.util.List<? extends com.github.dawidd6.andttt.proto.ClientOrBuilder> 
         getClientsOrBuilderList() {
      if (clientsBuilder_ != null) {
        return clientsBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(clients_);
      }
    }
    /**
     * <code>repeated .proto.Client clients = 2;</code>
     */
    public com.github.dawidd6.andttt.proto.Client.Builder addClientsBuilder() {
      return getClientsFieldBuilder().addBuilder(
          com.github.dawidd6.andttt.proto.Client.getDefaultInstance());
    }
    /**
     * <code>repeated .proto.Client clients = 2;</code>
     */
    public com.github.dawidd6.andttt.proto.Client.Builder addClientsBuilder(
        int index) {
      return getClientsFieldBuilder().addBuilder(
          index, com.github.dawidd6.andttt.proto.Client.getDefaultInstance());
    }
    /**
     * <code>repeated .proto.Client clients = 2;</code>
     */
    public java.util.List<com.github.dawidd6.andttt.proto.Client.Builder> 
         getClientsBuilderList() {
      return getClientsFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        com.github.dawidd6.andttt.proto.Client, com.github.dawidd6.andttt.proto.Client.Builder, com.github.dawidd6.andttt.proto.ClientOrBuilder> 
        getClientsFieldBuilder() {
      if (clientsBuilder_ == null) {
        clientsBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            com.github.dawidd6.andttt.proto.Client, com.github.dawidd6.andttt.proto.Client.Builder, com.github.dawidd6.andttt.proto.ClientOrBuilder>(
                clients_,
                ((bitField0_ & 0x00000002) == 0x00000002),
                getParentForChildren(),
                isClean());
        clients_ = null;
      }
      return clientsBuilder_;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:proto.ApiClients)
  }

  // @@protoc_insertion_point(class_scope:proto.ApiClients)
  private static final com.github.dawidd6.andttt.proto.ApiClients DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.github.dawidd6.andttt.proto.ApiClients();
  }

  public static com.github.dawidd6.andttt.proto.ApiClients getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ApiClients>
      PARSER = new com.google.protobuf.AbstractParser<ApiClients>() {
    public ApiClients parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new ApiClients(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ApiClients> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ApiClients> getParserForType() {
    return PARSER;
  }

  public com.github.dawidd6.andttt.proto.ApiClients getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

