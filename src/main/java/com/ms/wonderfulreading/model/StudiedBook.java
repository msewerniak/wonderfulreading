package com.ms.wonderfulreading.model;

import java.util.List;

public record StudiedBook(List<Lesson> days, Book book) {
}
