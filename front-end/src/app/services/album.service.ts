import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Album } from '../albums/model/album.model';
import { Photo } from '../albums/model/photo.model';

@Injectable({
  providedIn: 'root'
})
export class AlbumService {
  private albumsUrl = 'https://jsonplaceholder.typicode.com/albums';
  private photosUrl = 'https://jsonplaceholder.typicode.com/photos';

  constructor(private http: HttpClient) { }

    getAlbums(): Observable<Album[]>{
      return this.http.get<Album[]>(this.albumsUrl);
    }

    getPhotosByAlbumId(albumId: number): Observable<Photo[]>{
      return this.http.get<Photo[]>(`${this.photosUrl}?albumId=${albumId}`);
    }
}
