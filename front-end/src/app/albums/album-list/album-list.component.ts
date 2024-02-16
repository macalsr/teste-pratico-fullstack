import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { AlbumService } from '../../services/album.service';
import { Album } from '../model/album.model';
import { Photo } from '../model/photo.model';
import { MatDialog } from '@angular/material/dialog';
import { AlbumPhotosModalComponent } from '../album-photos-modal/album-photos-modal.component';
import { UsuarioService } from '../../services/usuario.service';

@Component({
  selector: 'app-album-list',
  templateUrl: './album-list.component.html',
  styleUrls: ['./album-list.component.scss']
})
export class AlbumListComponent implements OnInit {
  albums$!: Observable<Album[]>;
  albumThumbnails: { album: Album, thumbnailUrl: string }[] = [];
  albumPhotos: { albumId: number, photos: Photo[] }[] = [];

  constructor(private albumService: AlbumService, private usuarioService: UsuarioService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.albums$ = this.albumService.getAlbums();
    this.albums$.subscribe(albums => {
      albums.forEach(album => {
        this.albumService.getPhotosByAlbumId(album.id).subscribe(photos => {
          if (photos.length > 0) {
            const thumbnailUrl = photos[0].thumbnailUrl;
            this.albumThumbnails.push({ album, thumbnailUrl });
            this.albumPhotos.push({ albumId: album.id, photos: photos.slice(0, 10) });
          }
        });
      });
    });
  }

  showFirstTenPhotos(albumId: number): void{
    this.albumService.getPhotosByAlbumId(albumId).subscribe(photos => {
      const firstTenPhotos = photos.slice(0,10);

      const dialogRef = this.dialog.open(AlbumPhotosModalComponent, {
        maxWidth: '990%',
        maxHeight: '90vh',
        data:{
          photos: firstTenPhotos
        }
      });

      dialogRef.afterClosed().subscribe(result => {
        console.log('O modal foi fechado', result)
      });
    });
  }
  deslogar(){
    this.usuarioService.deslogar();
  }
}
