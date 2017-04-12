//
// Created by 马壮 on 2017/4/7.
//

#include <jni.h>
#include "md5.h"

jstring calc_md5(JNIEnv* env, jstring src, const char* salt) {
    MD5 md5;

    char* rtn = NULL;
    jclass clsstring = env->FindClass("java/lang/String");
    jstring strencode = env->NewStringUTF("utf-8");
    jmethodID mid = env->GetMethodID(clsstring, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr= (jbyteArray)env->CallObjectMethod(src, mid, strencode);
    jsize alen = env->GetArrayLength(barr);
    jbyte* ba = env->GetByteArrayElements(barr, JNI_FALSE);
    if (alen > 0)
    {
        size_t salt_len = strlen(salt);
        rtn = (char*)malloc(alen + salt_len + 1);
        memcpy(rtn, ba, alen);
        memcpy(rtn + alen, salt, salt_len);
        rtn[alen + salt_len] = 0;
    }
    env->ReleaseByteArrayElements(barr, ba, 0);

    md5.update(rtn);

    delete rtn;

    return env->NewStringUTF(md5.toString().c_str());
}

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jstring JNICALL Java_org_mazhuang_wechattoutiao_util_Security_encMethod11(
        JNIEnv* env,
        jclass thiz,
        jstring src) {
    return calc_md5(env, src, "");
}

JNIEXPORT jstring JNICALL Java_org_mazhuang_wechattoutiao_util_Security_encMethod12(
        JNIEnv* env,
        jclass thiz,
        jstring src) {
    return calc_md5(env, src, "sogouapp");
}

JNIEXPORT jstring JNICALL Java_org_mazhuang_wechattoutiao_util_Security_encMethod13(
        JNIEnv* env,
        jclass thiz,
        jstring src) {
    return calc_md5(env, src, "sogou_xid");
}

JNIEXPORT jstring JNICALL Java_org_mazhuang_wechattoutiao_util_Security_getAlgorithm(
        JNIEnv* env,
        jclass thiz) {
    return env->NewStringUTF("AES");
}


JNIEXPORT jstring JNICALL Java_org_mazhuang_wechattoutiao_util_Security_getTransformation(
        JNIEnv* env,
        jclass thiz) {
    return env->NewStringUTF("AES/CBC/PKCS7Padding");
}

JNIEXPORT jstring JNICALL Java_org_mazhuang_wechattoutiao_util_Security_getKey(
        JNIEnv* env,
        jclass thiz) {
    return env->NewStringUTF("sougouappno.0001");
}

JNIEXPORT jstring JNICALL Java_org_mazhuang_wechattoutiao_util_Security_getEncoding(
        JNIEnv* env,
        jclass thiz) {
    return env->NewStringUTF("UTF-8");
}

#ifdef __cplusplus
}
#endif