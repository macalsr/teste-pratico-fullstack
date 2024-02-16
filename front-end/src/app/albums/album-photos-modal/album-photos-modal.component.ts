import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
@Component({
  selector: 'app-album-photos-modal',
  templateUrl: './album-photos-modal.component.html',
  styleUrl: './album-photos-modal.component.scss'
})
export class AlbumPhotosModalComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: {photos:any[]}){}

}
