import ZIPFoundation
import composeApp

class UnzipperImpl: Unzipper {
    func readArchive(
        source: any Kotlinx_io_coreSource,
        handleFile: @escaping (String) -> any Kotlinx_io_coreSink,
        handleDirectory: @escaping (String) -> Void
    ) throws {
        let data = Unzipper_iosKt.toNSData(source)
        let archive = try Archive(data: data, accessMode: .read)
        for entry in archive {
            switch entry.type {
            case .file:
                let sink = handleFile(entry.path)
                _ = try archive.extract(
                    entry,
                    skipCRC32: true,
                    consumer: { data -> Void in
                        sink
                            .write(
                                source:Unzipper_iosKt.toByteArray(data),
                                startIndex: 0,
                                endIndex: Int32(data.count)
                            )
                    }
                )
                sink.close()
            case .directory: handleDirectory(entry.path)
            case .symlink: break
            }
        }
    }
}
