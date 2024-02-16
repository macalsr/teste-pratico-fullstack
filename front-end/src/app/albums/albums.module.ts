import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AlbumListComponent } from './album-list/album-list.component';
import { FormsModule } from '@angular/forms';
import { AlbumPhotosModalComponent } from './album-photos-modal/album-photos-modal.component'
import { MatDialogModule } from '@angular/material/dialog';
import { MatIcon } from '@angular/material/icon';


@NgModule({
  declarations: [
    AlbumListComponent,
    AlbumPhotosModalComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    MatDialogModule,
    MatIcon
  ]
})
export class AlbumsModule { }
