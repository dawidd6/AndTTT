// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: enums.proto

package com.github.dawidd6.andttt.proto;

/**
 * Protobuf enum {@code proto.Restart}
 */
public enum Restart
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>UNSPECIFIED = 0;</code>
   */
  UNSPECIFIED(0),
  /**
   * <code>REQUESTED = 1;</code>
   */
  REQUESTED(1),
  /**
   * <code>APPROVED = 2;</code>
   */
  APPROVED(2),
  /**
   * <code>DENIED = 3;</code>
   */
  DENIED(3),
  UNRECOGNIZED(-1),
  ;

  /**
   * <code>UNSPECIFIED = 0;</code>
   */
  public static final int UNSPECIFIED_VALUE = 0;
  /**
   * <code>REQUESTED = 1;</code>
   */
  public static final int REQUESTED_VALUE = 1;
  /**
   * <code>APPROVED = 2;</code>
   */
  public static final int APPROVED_VALUE = 2;
  /**
   * <code>DENIED = 3;</code>
   */
  public static final int DENIED_VALUE = 3;


  public final int getNumber() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalArgumentException(
          "Can't get the number of an unknown enum value.");
    }
    return value;
  }

  /**
   * @deprecated Use {@link #forNumber(int)} instead.
   */
  @java.lang.Deprecated
  public static Restart valueOf(int value) {
    return forNumber(value);
  }

  public static Restart forNumber(int value) {
    switch (value) {
      case 0: return UNSPECIFIED;
      case 1: return REQUESTED;
      case 2: return APPROVED;
      case 3: return DENIED;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<Restart>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      Restart> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<Restart>() {
          public Restart findValueByNumber(int number) {
            return Restart.forNumber(number);
          }
        };

  public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
    return getDescriptor().getValues().get(ordinal());
  }
  public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }
  public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
    return com.github.dawidd6.andttt.proto.Enums.getDescriptor().getEnumTypes().get(1);
  }

  private static final Restart[] VALUES = values();

  public static Restart valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    if (desc.getIndex() == -1) {
      return UNRECOGNIZED;
    }
    return VALUES[desc.getIndex()];
  }

  private final int value;

  private Restart(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:proto.Restart)
}

