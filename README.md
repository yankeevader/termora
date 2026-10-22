# Tunnelkeeper

**Tunnelkeeper** is Off The Map's local-first terminal and SSH client, built from the
[Termora](https://github.com/TermoraDev/termora) project. This is the **`otm-ui`**
fork, not an official Termora release.

## What this fork changes

- **Hosts / Snippets sidebar:** docked tabs for saved connections and commands.
  Create a host from My Hosts, open the full snippet manager, and double-click
  a snippet to execute it in the active terminal.
- **Local-first behavior:** analytics/telemetry, update checking, cloud account
  synchronization, and background plugin marketplace polling are disabled.
- **Deliberate connections still work:** SSH, SFTP, RDP, Telnet and other explicitly
  used network features are not blocked. "Local-first" does **not** mean offline-only.
- **Portable Windows ZIP:** bundled Java runtime with local data storage.

## Windows x64 portable build

1. Open the [Tunnelkeeper Windows Portable workflow](https://github.com/yankeevader/termora/actions/workflows/windows.yml).
2. Select the latest **successful** run on `otm-ui` and download the
   `Tunnelkeeper-Windows-x64-portable` artifact.
3. Extract the ZIP inside the downloaded artifact to a **writable folder**,
   for example `D:\Tools\Tunnelkeeper`. Do not run it from inside a ZIP file
   or extract it into a protected directory such as `Program Files`.
4. Launch `Tunnelkeeper.exe` from the extracted `Tunnelkeeper` folder.

The package is **portable**, not an installer, and includes its own Java runtime.
When launched from this ZIP layout, the app writes its hosts, keys, snippets,
settings and logs into the `data` folder beside `Tunnelkeeper.exe`. Copy the
whole extracted folder if you want to move the application and its data.

An existing non-portable Termora or Tunnelkeeper installation may use the old
`~/.termora` data directory. The portable package does not automatically copy
that profile. Back up that directory before migrating, especially if it contains
private keys.

### Build from source (Windows x64)

Prerequisites: JetBrains Runtime JDK 21, the included Gradle wrapper,
and Info-ZIP `zip` / `unzip` tools available on `PATH`.
To produce the Windows package:

```powershell
.\gradlew.bat :check-license :portableDist
```

The resulting file is
`build/distributions/tunnelkeeper-2.0.0-beta.15-windows-x86-64-portable.zip`
(the architecture spelling follows Gradle's detected architecture, so use the
`*-portable.zip` file rather than depending on an exact filename).

The workflow also checks the archive contains the launcher, runtime and
data directory, and publishes a SHA-256 checksum beside the ZIP.

**Build status:** source and packaging scripts are available, but a successful
Windows CI run and a real Windows launch test are required before this can be
called a tested release.

## Source, credits and licensing

Tunnelkeeper is a modified distribution of **Termora**. Credit for the original
project and its contributors belongs to
[TermoraDev/termora](https://github.com/TermoraDev/termora).
This fork preserves the upstream third-party notices in `THIRDPARTY`.

Termora is offered by its authors under AGPL-3.0 or a separate commercial
license. Unless a separate commercial license has been granted, distribute
this modified fork in accordance with **AGPL-3.0**, including corresponding
source and required notices.
