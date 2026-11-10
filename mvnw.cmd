<# : batch portion
@REM ----------------------------------------------------------------------------
@REM Licensed to the Apache Software Foundation (ASF) under one
@REM or more contributor license agreements. See the NOTICE file
@REM distributed with this work for additional information.
@REM The ASF licenses this file to You under the Apache License, Version 2.0.
@REM ----------------------------------------------------------------------------
@REM Apache Maven Wrapper startup batch script, version 3.3.4
@IF "%__MVNW_ARG0_NAME__%"=="" (SET __MVNW_ARG0_NAME__=%~nx0)
@SET __MVNW_CMD__=
@SET __MVNW_ERROR__=
@SET __MVNW_PSMODULEP_SAVE=%PSModulePath%
@SET PSModulePath=
@FOR /F "usebackq tokens=1* delims==" %%A IN (`powershell -noprofile "& {$scriptDir='%~dp0'; $script='%~f0'; icm -ScriptBlock ([Scriptblock]::Create((Get-Content -Raw '%~f0'))) -NoNewScope}"`) DO @(
  IF "%%A"=="MVN_CMD" (set __MVNW_CMD__=%%B) ELSE IF "%%B"=="" (echo %%A) ELSE (echo %%A=%%B)
)
@SET __MVNW_PSMODULEP_SAVE=
@SET __MVNW_ARG0_NAME__=
@IF NOT "%__MVNW_CMD__%"=="" ("%__MVNW_CMD__%" %*)
@echo Cannot start maven from wrapper >&2 && exit /b 1
@GOTO :EOF
: end batch / begin powershell #>

$ErrorActionPreference = "Stop"
$scriptDir = if ($PSScriptRoot) { $PSScriptRoot } else { $scriptDir }
$propertiesPath = Join-Path $scriptDir ".mvn/wrapper/maven-wrapper.properties"
$line = Get-Content -LiteralPath $propertiesPath | Where-Object { $_ -match '^\s*distributionUrl=' } | Select-Object -First 1
if (-not $line) { throw "distributionUrl is missing in $propertiesPath" }
$distributionUrl = ($line -replace '^\s*distributionUrl=', '').Replace('\:', ':')
$distributionName = [IO.Path]::GetFileName(([Uri]$distributionUrl).AbsolutePath)
$distributionVersion = $distributionName -replace '^apache-maven-', '' -replace '-bin\.zip$', ''
$distributionBase = if ($env:MAVEN_USER_HOME) { $env:MAVEN_USER_HOME } else { Join-Path $HOME ".m2" }
$distributionDir = Join-Path (Join-Path $distributionBase "wrapper/dists") "apache-maven-$distributionVersion"
$mavenHome = Join-Path $distributionDir "apache-maven-$distributionVersion"
$mavenCommand = Join-Path $mavenHome "bin/mvn.cmd"
if (-not (Test-Path -LiteralPath $mavenCommand)) {
  New-Item -ItemType Directory -Force -Path $distributionDir | Out-Null
  $archive = Join-Path $distributionDir $distributionName
  Invoke-WebRequest -Uri $distributionUrl -OutFile $archive
  Expand-Archive -LiteralPath $archive -DestinationPath $distributionDir -Force
  Remove-Item -LiteralPath $archive -Force
}
if (-not (Test-Path -LiteralPath $mavenCommand)) { throw "Maven was not found at $mavenCommand" }
Write-Output "MVN_CMD=$mavenCommand"
