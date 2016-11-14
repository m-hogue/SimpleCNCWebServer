var gulp = require('gulp');
var uglify = require('gulp-uglify');
var streamify = require('gulp-streamify');
var rename = require('gulp-rename');
var gulpif = require('gulp-if');
var rev = require('gulp-rev');
var inject = require('gulp-inject');

var browserify = require('browserify');
var babelify = require('babelify');
var del = require('del');
var source = require('vinyl-source-stream');
var buffer = require('vinyl-buffer');
var resolve = require('resolve');
var series = require('stream-series');
var argv = require('yargs').argv;

var production = (process.env.NODE_ENV === 'production' || argv.production);

gulp.task('default', ['clean', 'inject'], function() {});

gulp.task('clean', function(cb) {
  return del(['./public'], cb);
});


gulp.task('inject', ['clean', 'vendor', 'scripts', 'css', 'favicon'], function() {
  var vendor = gulp.src('./public/scripts/vendor*.js', {read: false});
  var scripts = gulp.src('./public/scripts/scripts*.js', {read: false});
  var css = gulp.src('./public/css/*css', {read: false});
  var favicon = gulp.src('./public/favicon/*.png', {read: false});


  gulp.src('./webapp/index.html')
    .pipe(inject(series(vendor, scripts, css, favicon), {ignorePath: '/public'}))
    .pipe(gulp.dest('./public'));
});

gulp.task('css', ['clean'], function() {
    var css = gulp.src([
            './node_modules/bootstrap/dist/css/bootstrap.css',
            './node_modules/bootstrap/dist/css/bootstrap-theme.css',
            './webapp/css/app.scss'
        ]).pipe(gulp.dest('./public/css'));
    return css;
});

gulp.task('favicon', ['clean'], function() {
    var fav = gulp.src([
            './webapp/icon/favicon-16x16.png',
            './webapp/icon/favicon-32x32.png',
            './webapp/icon/favicon-96x96.png',
        ]).pipe(gulp.dest('./public/favicon'));
    return fav;
});

gulp.task('vendor', ['clean'], function() {
  var b = browserify({debug: !production});

  getNPMPackageIds().forEach(function(id) {
    b.require(resolve.sync(id), {expose: id});
  });

  return bundle('vendor.js', b);
});

gulp.task('scripts', ['clean'], function() {
  var b = browserify({debug: !production})
    .require('./webapp/js/app.js', {entry: true})
    .transform(babelify);

  getNPMPackageIds().forEach(function(id) {
    b.external(id);
  });

  return bundle('scripts.js', b);
});

function bundle(name, b) {
  return b.bundle().pipe(source(name))
    .pipe(gulpif(production, streamify(uglify())))
    .pipe(rename(name.substring(0, name.lastIndexOf('.js')) + '.min.js'))
    .pipe(buffer())
    .pipe(rev())
    .pipe(gulp.dest('./public/scripts'));
}

function getNPMPackageIds() {
  // read package.json and get dependencies' package ids
  var packageManifest = {};
  try {
    packageManifest = require('./package.json');
  } catch (e) {
    // does not have a package.json manifest
  }
  return Object.keys(packageManifest.dependencies) || [];
}