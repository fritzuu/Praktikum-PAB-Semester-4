package com.praktikumpab.note_manager.data

import com.praktikumpab.note_manager.model.Note

object NoteData {
    val sampleNotes = listOf(
        Note(1, "Materi Pemrograman Mobile", "Belajar Jetpack Compose dan State Management.", "2026-05-20", "Kuliah", isPinned = true),
        Note(2, "Deadline Tugas Basis Data", "Kumpulkan laporan praktikum di LMS.", "2026-05-27", "Tugas", deadline = "2026-05-28 20:00", isFavorite = true),
        Note(3, "Ide Proyek Akhir", "Aplikasi manajemen catatan dengan fitur sharing.", "2026-05-22", "Ide"),
        Note(4, "Jadwal Ujian Tengah Semester", "Cek jadwal di portal akademik.", "2026-05-23", "Kuliah", isPinned = true),
        Note(5, "Belanja Kebutuhan Kos", "Beli sabun, sampo, dan deterjen.", "2026-05-24", "Pribadi"),
        Note(6, "Rapat Organisasi", "Bahas proker bulan depan jam 7 malam.", "2026-05-25", "Organisasi", deadline = "2026-06-01 19:00"),
        Note(7, "Materi Jaringan Komputer", "Pelajari TCP/IP Layer dan Subnetting.", "2026-05-26", "Kuliah"),
        Note(8, "Booking Tiket Pulang", "Cek harga tiket kereta api untuk libur semester.", "2026-05-27", "Pribadi"),
        Note(9, "Deadline Project Android", "Fix bug di bagian navigation dan database.", "2026-05-27", "Tugas", deadline = "2026-05-30 23:59"),
        Note(10, "Review Materi Statistika", "Kerjakan latihan soal distribusi normal.", "2026-05-28", "Kuliah")
    )
}
