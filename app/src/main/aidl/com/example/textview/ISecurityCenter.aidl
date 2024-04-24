// ISecurityCenter.aidl
package com.example.textview;

import java.lang.String;
interface ISecurityCenter {
           String encrypt(String content);
           String decrypt(String password);
}