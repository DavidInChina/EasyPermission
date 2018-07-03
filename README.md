# Android自动获取敏感权限并申请
本工具库采用链式调用请求、**运行时注解**回调请求结果，提供单个、多个的权限调用方法，
同样而也支持根据**清单文件自动读取**dangerous permissions来一次性请求所有需要请求的权限。


运行效果：
![运行效果](https://image.ibb.co/daSZCF/device_2017_08_13_171734.png)

Android敏感权限：
![权限](https://image.ibb.co/hNVnXF/permission.jpg)

## Installation

* gradle

```
     compile 'com.davidinchina:easylibrary:1.0.0'
```
## Usage
* 调用 **单个权限申请**:
 ```java
EasyPermission.with(this).code(REQUEST_CODE).permissions(
                Manifest.permission.READ_EXTERNAL_STORAGE
        ).request();
 ```
 
* 调用 **多个权限申请**:
 ```java
 EasyPermission.with(this).code(REQUEST_CODE).permissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION).request();
 ```
 
* 调用 **自动获取敏感权限一次性申请**:
 ```java
 EasyPermission.with(this).code(REQUEST_CODE).request();
 ```
 
 ###处理请求回调
  ```java
 @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermission.handleResult(this, requestCode, permissions, grantResults);//处理权限申请回调结果
    }
 ```
  ###权限请求成功回调
  ```java
 @OnEasyPermissionSuccess(REQUEST_CODE)
    public void onBasicPermissionSuccess() {
        Toast.makeText(this, "权限申请成功", Toast.LENGTH_SHORT).show();
    }
 ```
 ###权限请求失败回调
  ```java
 @OnEasyPermissionFailed(REQUEST_CODE)
    public void onBasicPermissionFailed() {
        Toast.makeText(this, "权限申请失败，请移步系统设置修改权限", Toast.LENGTH_SHORT).show();
    }
 ```


## License
```license
Copyright 2017 DavidinChina

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
