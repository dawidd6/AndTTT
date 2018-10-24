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
  public interface ClientOrBuilder extends
      // @@protoc_insertion_point(interface_extends:proto.Client)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string name = 1;</code>
     */
    java.lang.String getName();
    /**
     * <code>string name = 1;</code>
     */
    com.google.protobuf.ByteString
        getNameBytes();

    /**
     * <code>string room = 2;</code>
     */
    java.lang.String getRoom();
    /**
     * <code>string room = 2;</code>
     */
    com.google.protobuf.ByteString
        getRoomBytes();

    /**
     * <code>string addr = 3;</code>
     */
    java.lang.String getAddr();
    /**
     * <code>string addr = 3;</code>
     */
    com.google.protobuf.ByteString
        getAddrBytes();

    /**
     * <code>.google.protobuf.Timestamp since = 4;</code>
     */
    boolean hasSince();
    /**
     * <code>.google.protobuf.Timestamp since = 4;</code>
     */
    com.google.protobuf.Timestamp getSince();
    /**
     * <code>.google.protobuf.Timestamp since = 4;</code>
     */
    com.google.protobuf.TimestampOrBuilder getSinceOrBuilder();

    /**
     * <code>string symbol = 5;</code>
     */
    java.lang.String getSymbol();
    /**
     * <code>string symbol = 5;</code>
     */
    com.google.protobuf.ByteString
        getSymbolBytes();

    /**
     * <code>bool turn = 6;</code>
     */
    boolean getTurn();
  }
  /**
   * Protobuf type {@code proto.Client}
   */
  public  static final class Client extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:proto.Client)
      ClientOrBuilder {
    // Use Client.newBuilder() to construct.
    private Client(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Client() {
      name_ = "";
      room_ = "";
      addr_ = "";
      symbol_ = "";
      turn_ = false;
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private Client(
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
              java.lang.String s = input.readStringRequireUtf8();

              name_ = s;
              break;
            }
            case 18: {
              java.lang.String s = input.readStringRequireUtf8();

              room_ = s;
              break;
            }
            case 26: {
              java.lang.String s = input.readStringRequireUtf8();

              addr_ = s;
              break;
            }
            case 34: {
              com.google.protobuf.Timestamp.Builder subBuilder = null;
              if (since_ != null) {
                subBuilder = since_.toBuilder();
              }
              since_ = input.readMessage(com.google.protobuf.Timestamp.parser(), extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(since_);
                since_ = subBuilder.buildPartial();
              }

              break;
            }
            case 42: {
              java.lang.String s = input.readStringRequireUtf8();

              symbol_ = s;
              break;
            }
            case 48: {

              turn_ = input.readBool();
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
      return com.github.dawidd6.andttt.proto.ClientOuterClass.internal_static_proto_Client_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.github.dawidd6.andttt.proto.ClientOuterClass.internal_static_proto_Client_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.github.dawidd6.andttt.proto.ClientOuterClass.Client.class, com.github.dawidd6.andttt.proto.ClientOuterClass.Client.Builder.class);
    }

    public static final int NAME_FIELD_NUMBER = 1;
    private volatile java.lang.Object name_;
    /**
     * <code>string name = 1;</code>
     */
    public java.lang.String getName() {
      java.lang.Object ref = name_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        name_ = s;
        return s;
      }
    }
    /**
     * <code>string name = 1;</code>
     */
    public com.google.protobuf.ByteString
        getNameBytes() {
      java.lang.Object ref = name_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        name_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ROOM_FIELD_NUMBER = 2;
    private volatile java.lang.Object room_;
    /**
     * <code>string room = 2;</code>
     */
    public java.lang.String getRoom() {
      java.lang.Object ref = room_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        room_ = s;
        return s;
      }
    }
    /**
     * <code>string room = 2;</code>
     */
    public com.google.protobuf.ByteString
        getRoomBytes() {
      java.lang.Object ref = room_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        room_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ADDR_FIELD_NUMBER = 3;
    private volatile java.lang.Object addr_;
    /**
     * <code>string addr = 3;</code>
     */
    public java.lang.String getAddr() {
      java.lang.Object ref = addr_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        addr_ = s;
        return s;
      }
    }
    /**
     * <code>string addr = 3;</code>
     */
    public com.google.protobuf.ByteString
        getAddrBytes() {
      java.lang.Object ref = addr_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        addr_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int SINCE_FIELD_NUMBER = 4;
    private com.google.protobuf.Timestamp since_;
    /**
     * <code>.google.protobuf.Timestamp since = 4;</code>
     */
    public boolean hasSince() {
      return since_ != null;
    }
    /**
     * <code>.google.protobuf.Timestamp since = 4;</code>
     */
    public com.google.protobuf.Timestamp getSince() {
      return since_ == null ? com.google.protobuf.Timestamp.getDefaultInstance() : since_;
    }
    /**
     * <code>.google.protobuf.Timestamp since = 4;</code>
     */
    public com.google.protobuf.TimestampOrBuilder getSinceOrBuilder() {
      return getSince();
    }

    public static final int SYMBOL_FIELD_NUMBER = 5;
    private volatile java.lang.Object symbol_;
    /**
     * <code>string symbol = 5;</code>
     */
    public java.lang.String getSymbol() {
      java.lang.Object ref = symbol_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        symbol_ = s;
        return s;
      }
    }
    /**
     * <code>string symbol = 5;</code>
     */
    public com.google.protobuf.ByteString
        getSymbolBytes() {
      java.lang.Object ref = symbol_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        symbol_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TURN_FIELD_NUMBER = 6;
    private boolean turn_;
    /**
     * <code>bool turn = 6;</code>
     */
    public boolean getTurn() {
      return turn_;
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
      if (!getNameBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, name_);
      }
      if (!getRoomBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, room_);
      }
      if (!getAddrBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, addr_);
      }
      if (since_ != null) {
        output.writeMessage(4, getSince());
      }
      if (!getSymbolBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 5, symbol_);
      }
      if (turn_ != false) {
        output.writeBool(6, turn_);
      }
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getNameBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, name_);
      }
      if (!getRoomBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, room_);
      }
      if (!getAddrBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, addr_);
      }
      if (since_ != null) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(4, getSince());
      }
      if (!getSymbolBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, symbol_);
      }
      if (turn_ != false) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(6, turn_);
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
      if (!(obj instanceof com.github.dawidd6.andttt.proto.ClientOuterClass.Client)) {
        return super.equals(obj);
      }
      com.github.dawidd6.andttt.proto.ClientOuterClass.Client other = (com.github.dawidd6.andttt.proto.ClientOuterClass.Client) obj;

      boolean result = true;
      result = result && getName()
          .equals(other.getName());
      result = result && getRoom()
          .equals(other.getRoom());
      result = result && getAddr()
          .equals(other.getAddr());
      result = result && (hasSince() == other.hasSince());
      if (hasSince()) {
        result = result && getSince()
            .equals(other.getSince());
      }
      result = result && getSymbol()
          .equals(other.getSymbol());
      result = result && (getTurn()
          == other.getTurn());
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + NAME_FIELD_NUMBER;
      hash = (53 * hash) + getName().hashCode();
      hash = (37 * hash) + ROOM_FIELD_NUMBER;
      hash = (53 * hash) + getRoom().hashCode();
      hash = (37 * hash) + ADDR_FIELD_NUMBER;
      hash = (53 * hash) + getAddr().hashCode();
      if (hasSince()) {
        hash = (37 * hash) + SINCE_FIELD_NUMBER;
        hash = (53 * hash) + getSince().hashCode();
      }
      hash = (37 * hash) + SYMBOL_FIELD_NUMBER;
      hash = (53 * hash) + getSymbol().hashCode();
      hash = (37 * hash) + TURN_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
          getTurn());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.github.dawidd6.andttt.proto.ClientOuterClass.Client parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.github.dawidd6.andttt.proto.ClientOuterClass.Client parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.github.dawidd6.andttt.proto.ClientOuterClass.Client parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.github.dawidd6.andttt.proto.ClientOuterClass.Client parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.github.dawidd6.andttt.proto.ClientOuterClass.Client parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.github.dawidd6.andttt.proto.ClientOuterClass.Client parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.github.dawidd6.andttt.proto.ClientOuterClass.Client parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.github.dawidd6.andttt.proto.ClientOuterClass.Client parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.github.dawidd6.andttt.proto.ClientOuterClass.Client parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.github.dawidd6.andttt.proto.ClientOuterClass.Client parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.github.dawidd6.andttt.proto.ClientOuterClass.Client parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.github.dawidd6.andttt.proto.ClientOuterClass.Client parseFrom(
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
    public static Builder newBuilder(com.github.dawidd6.andttt.proto.ClientOuterClass.Client prototype) {
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
     * Protobuf type {@code proto.Client}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:proto.Client)
        com.github.dawidd6.andttt.proto.ClientOuterClass.ClientOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.github.dawidd6.andttt.proto.ClientOuterClass.internal_static_proto_Client_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.github.dawidd6.andttt.proto.ClientOuterClass.internal_static_proto_Client_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.github.dawidd6.andttt.proto.ClientOuterClass.Client.class, com.github.dawidd6.andttt.proto.ClientOuterClass.Client.Builder.class);
      }

      // Construct using com.github.dawidd6.andttt.proto.ClientOuterClass.Client.newBuilder()
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
        name_ = "";

        room_ = "";

        addr_ = "";

        if (sinceBuilder_ == null) {
          since_ = null;
        } else {
          since_ = null;
          sinceBuilder_ = null;
        }
        symbol_ = "";

        turn_ = false;

        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.github.dawidd6.andttt.proto.ClientOuterClass.internal_static_proto_Client_descriptor;
      }

      public com.github.dawidd6.andttt.proto.ClientOuterClass.Client getDefaultInstanceForType() {
        return com.github.dawidd6.andttt.proto.ClientOuterClass.Client.getDefaultInstance();
      }

      public com.github.dawidd6.andttt.proto.ClientOuterClass.Client build() {
        com.github.dawidd6.andttt.proto.ClientOuterClass.Client result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.github.dawidd6.andttt.proto.ClientOuterClass.Client buildPartial() {
        com.github.dawidd6.andttt.proto.ClientOuterClass.Client result = new com.github.dawidd6.andttt.proto.ClientOuterClass.Client(this);
        result.name_ = name_;
        result.room_ = room_;
        result.addr_ = addr_;
        if (sinceBuilder_ == null) {
          result.since_ = since_;
        } else {
          result.since_ = sinceBuilder_.build();
        }
        result.symbol_ = symbol_;
        result.turn_ = turn_;
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
        if (other instanceof com.github.dawidd6.andttt.proto.ClientOuterClass.Client) {
          return mergeFrom((com.github.dawidd6.andttt.proto.ClientOuterClass.Client)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.github.dawidd6.andttt.proto.ClientOuterClass.Client other) {
        if (other == com.github.dawidd6.andttt.proto.ClientOuterClass.Client.getDefaultInstance()) return this;
        if (!other.getName().isEmpty()) {
          name_ = other.name_;
          onChanged();
        }
        if (!other.getRoom().isEmpty()) {
          room_ = other.room_;
          onChanged();
        }
        if (!other.getAddr().isEmpty()) {
          addr_ = other.addr_;
          onChanged();
        }
        if (other.hasSince()) {
          mergeSince(other.getSince());
        }
        if (!other.getSymbol().isEmpty()) {
          symbol_ = other.symbol_;
          onChanged();
        }
        if (other.getTurn() != false) {
          setTurn(other.getTurn());
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
        com.github.dawidd6.andttt.proto.ClientOuterClass.Client parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.github.dawidd6.andttt.proto.ClientOuterClass.Client) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private java.lang.Object name_ = "";
      /**
       * <code>string name = 1;</code>
       */
      public java.lang.String getName() {
        java.lang.Object ref = name_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          name_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string name = 1;</code>
       */
      public com.google.protobuf.ByteString
          getNameBytes() {
        java.lang.Object ref = name_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          name_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string name = 1;</code>
       */
      public Builder setName(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        name_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string name = 1;</code>
       */
      public Builder clearName() {
        
        name_ = getDefaultInstance().getName();
        onChanged();
        return this;
      }
      /**
       * <code>string name = 1;</code>
       */
      public Builder setNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        name_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object room_ = "";
      /**
       * <code>string room = 2;</code>
       */
      public java.lang.String getRoom() {
        java.lang.Object ref = room_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          room_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string room = 2;</code>
       */
      public com.google.protobuf.ByteString
          getRoomBytes() {
        java.lang.Object ref = room_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          room_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string room = 2;</code>
       */
      public Builder setRoom(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        room_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string room = 2;</code>
       */
      public Builder clearRoom() {
        
        room_ = getDefaultInstance().getRoom();
        onChanged();
        return this;
      }
      /**
       * <code>string room = 2;</code>
       */
      public Builder setRoomBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        room_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object addr_ = "";
      /**
       * <code>string addr = 3;</code>
       */
      public java.lang.String getAddr() {
        java.lang.Object ref = addr_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          addr_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string addr = 3;</code>
       */
      public com.google.protobuf.ByteString
          getAddrBytes() {
        java.lang.Object ref = addr_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          addr_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string addr = 3;</code>
       */
      public Builder setAddr(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        addr_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string addr = 3;</code>
       */
      public Builder clearAddr() {
        
        addr_ = getDefaultInstance().getAddr();
        onChanged();
        return this;
      }
      /**
       * <code>string addr = 3;</code>
       */
      public Builder setAddrBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        addr_ = value;
        onChanged();
        return this;
      }

      private com.google.protobuf.Timestamp since_ = null;
      private com.google.protobuf.SingleFieldBuilderV3<
          com.google.protobuf.Timestamp, com.google.protobuf.Timestamp.Builder, com.google.protobuf.TimestampOrBuilder> sinceBuilder_;
      /**
       * <code>.google.protobuf.Timestamp since = 4;</code>
       */
      public boolean hasSince() {
        return sinceBuilder_ != null || since_ != null;
      }
      /**
       * <code>.google.protobuf.Timestamp since = 4;</code>
       */
      public com.google.protobuf.Timestamp getSince() {
        if (sinceBuilder_ == null) {
          return since_ == null ? com.google.protobuf.Timestamp.getDefaultInstance() : since_;
        } else {
          return sinceBuilder_.getMessage();
        }
      }
      /**
       * <code>.google.protobuf.Timestamp since = 4;</code>
       */
      public Builder setSince(com.google.protobuf.Timestamp value) {
        if (sinceBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          since_ = value;
          onChanged();
        } else {
          sinceBuilder_.setMessage(value);
        }

        return this;
      }
      /**
       * <code>.google.protobuf.Timestamp since = 4;</code>
       */
      public Builder setSince(
          com.google.protobuf.Timestamp.Builder builderForValue) {
        if (sinceBuilder_ == null) {
          since_ = builderForValue.build();
          onChanged();
        } else {
          sinceBuilder_.setMessage(builderForValue.build());
        }

        return this;
      }
      /**
       * <code>.google.protobuf.Timestamp since = 4;</code>
       */
      public Builder mergeSince(com.google.protobuf.Timestamp value) {
        if (sinceBuilder_ == null) {
          if (since_ != null) {
            since_ =
              com.google.protobuf.Timestamp.newBuilder(since_).mergeFrom(value).buildPartial();
          } else {
            since_ = value;
          }
          onChanged();
        } else {
          sinceBuilder_.mergeFrom(value);
        }

        return this;
      }
      /**
       * <code>.google.protobuf.Timestamp since = 4;</code>
       */
      public Builder clearSince() {
        if (sinceBuilder_ == null) {
          since_ = null;
          onChanged();
        } else {
          since_ = null;
          sinceBuilder_ = null;
        }

        return this;
      }
      /**
       * <code>.google.protobuf.Timestamp since = 4;</code>
       */
      public com.google.protobuf.Timestamp.Builder getSinceBuilder() {
        
        onChanged();
        return getSinceFieldBuilder().getBuilder();
      }
      /**
       * <code>.google.protobuf.Timestamp since = 4;</code>
       */
      public com.google.protobuf.TimestampOrBuilder getSinceOrBuilder() {
        if (sinceBuilder_ != null) {
          return sinceBuilder_.getMessageOrBuilder();
        } else {
          return since_ == null ?
              com.google.protobuf.Timestamp.getDefaultInstance() : since_;
        }
      }
      /**
       * <code>.google.protobuf.Timestamp since = 4;</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          com.google.protobuf.Timestamp, com.google.protobuf.Timestamp.Builder, com.google.protobuf.TimestampOrBuilder> 
          getSinceFieldBuilder() {
        if (sinceBuilder_ == null) {
          sinceBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
              com.google.protobuf.Timestamp, com.google.protobuf.Timestamp.Builder, com.google.protobuf.TimestampOrBuilder>(
                  getSince(),
                  getParentForChildren(),
                  isClean());
          since_ = null;
        }
        return sinceBuilder_;
      }

      private java.lang.Object symbol_ = "";
      /**
       * <code>string symbol = 5;</code>
       */
      public java.lang.String getSymbol() {
        java.lang.Object ref = symbol_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          symbol_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string symbol = 5;</code>
       */
      public com.google.protobuf.ByteString
          getSymbolBytes() {
        java.lang.Object ref = symbol_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          symbol_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string symbol = 5;</code>
       */
      public Builder setSymbol(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        symbol_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string symbol = 5;</code>
       */
      public Builder clearSymbol() {
        
        symbol_ = getDefaultInstance().getSymbol();
        onChanged();
        return this;
      }
      /**
       * <code>string symbol = 5;</code>
       */
      public Builder setSymbolBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        symbol_ = value;
        onChanged();
        return this;
      }

      private boolean turn_ ;
      /**
       * <code>bool turn = 6;</code>
       */
      public boolean getTurn() {
        return turn_;
      }
      /**
       * <code>bool turn = 6;</code>
       */
      public Builder setTurn(boolean value) {
        
        turn_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>bool turn = 6;</code>
       */
      public Builder clearTurn() {
        
        turn_ = false;
        onChanged();
        return this;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }


      // @@protoc_insertion_point(builder_scope:proto.Client)
    }

    // @@protoc_insertion_point(class_scope:proto.Client)
    private static final com.github.dawidd6.andttt.proto.ClientOuterClass.Client DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.github.dawidd6.andttt.proto.ClientOuterClass.Client();
    }

    public static com.github.dawidd6.andttt.proto.ClientOuterClass.Client getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Client>
        PARSER = new com.google.protobuf.AbstractParser<Client>() {
      public Client parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new Client(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Client> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<Client> getParserForType() {
      return PARSER;
    }

    public com.github.dawidd6.andttt.proto.ClientOuterClass.Client getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_proto_Client_descriptor;
  private static final 
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
      "\n\014client.proto\022\005proto\032\037google/protobuf/t" +
      "imestamp.proto\"{\n\006Client\022\014\n\004name\030\001 \001(\t\022\014" +
      "\n\004room\030\002 \001(\t\022\014\n\004addr\030\003 \001(\t\022)\n\005since\030\004 \001(" +
      "\0132\032.google.protobuf.Timestamp\022\016\n\006symbol\030" +
      "\005 \001(\t\022\014\n\004turn\030\006 \001(\010B!\n\037com.github.dawidd" +
      "6.andttt.protob\006proto3"
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
          com.google.protobuf.TimestampProto.getDescriptor(),
        }, assigner);
    internal_static_proto_Client_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_proto_Client_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_proto_Client_descriptor,
        new java.lang.String[] { "Name", "Room", "Addr", "Since", "Symbol", "Turn", });
    com.google.protobuf.TimestampProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}