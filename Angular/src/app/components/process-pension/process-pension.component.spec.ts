import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcessPensionComponent } from './process-pension.component';

describe('ProcessPensionComponent', () => {
  let component: ProcessPensionComponent;
  let fixture: ComponentFixture<ProcessPensionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProcessPensionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProcessPensionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
